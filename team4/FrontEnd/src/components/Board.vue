<!--<script src="../assets/js/jquery.colorPicker.js"></script>-->
<template>
  <!-- <button @click="drawARect">畫圖</button> -->
  <div>
    <div>
      <button id="createStickyNoteButton" @click="createStickyNote()">Add New StickyNote</button>
      <button id="createLineButton" @click="createLine()">Add New Line</button>
      <input v-model="myUserId" placeholder="input userName">
      <canvas id="canvas" ref='board' >
      </canvas>
    </div>

    <div class="container" oncontextmenu="return false">
      <div id="contextMenu" class="context-menu">
        <ul>
          <label>
            <li>ColorPicker
              <input type="color" id="favcolor" name="favcolor" list="colors" style="margin-left: 4rem" />
              <datalist id="colors">
                <option>#fffabb</option>
                <option>#c8dd57</option>
                <option>#7adbfa</option>
                <option>#ffaa61</option>
                <option>#c697ce</option>
                <option>#fefe45</option>
                <option>#e693b9</option>
                <option>#ffffff</option>
                <option>#da0063</option>
                <option>#5b9bd5</option>
              </datalist>
            </li>
          </label>
          <li id="delButton" name="delButton">Delete</li>
          <li id = "bringToFrontButton">BringToFront</li>
          <li id = "bringForwardButton">BringForward</li>
          <li id = "sendBackwardButton">SendBackward</li>
          <li id = "sendToBackButton">SendToBack</li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import { fabric } from 'fabric'
import { changeFigureOrderApi, getBoardContentApi } from '@/apis/boardApi'
import {
  createStickyNoteApi,
  changeStickyNoteContentApi,
  changeStickyNoteColorApi,
  resizeStickyNoteApi, moveStickyNoteApi, deleteStickyNoteApi
} from '@/apis/stickyNoteApi'
import { webSocketHostIp } from '../config/config.js'
import uuidGenerator from '../utils/uuidGenerator.js'
import {
  attachTextfigure,
  changeLinePath,
  createLineApi,
  deleteLine
} from '@/apis/lineApi'
export default {
  data () {
    return {
      boardId: '9b283903-027b-43ab-a92f-61e825c6f145',
      canvasContext: null,
      boardContent: null,
      canvas: null,
      time: 0,
      contextMenu: null,
      delButton: null,
      favcolor: null,
      bringToFrontButton: null,
      bringForwardButton: null,
      sendBackwardButton: null,
      sendToBackButton: null,
      activeObjects: null,
      socket: null,
      socketLoaded: null,
      userCursorList: [],
      myUserId: '7398cd26-da85-4c05-b04b-122e73888dfc',
      mouseData: null,
      updateCursorFlag: true,
      updateFigureFlag: true,
      snapDistance: 80,
      linelist: [],
      associationMap: []
    }
  },
  created () {
    this.socketInit()
  },
  destroyed: function () { // 离开页面生命周期函数
    this.websocketclose()
  },
  async mounted () {
    this.getBoardContent()
    this.initCanvas()
    this.canvas.renderAll()
    this.contextMenu = document.getElementById('contextMenu')
    this.delButton = document.getElementById('delButton')
    this.favcolor = document.getElementById('favcolor')
    this.bringToFrontButton = document.getElementById('bringToFrontButton')
    this.bringForwardButton = document.getElementById('bringForwardButton')
    this.sendBackwardButton = document.getElementById('sendBackwardButton')
    this.sendToBackButton = document.getElementById('sendToBackButton')
    this.listenEventsOnCanvas()
    this.socket.onopen = this.websocketonopen
    this.socket.onerror = this.websocketonerror
    this.socket.onmessage = this.websocketonmessage
    this.socket.onclose = this.websocketclose
    this.timer = setInterval(this.updateFlag, 100)
  },
  methods: {
    updateFlag () {
      this.updateCursorFlag = true
      this.updateFigureFlag = true
    },
    sendMouseData () {
      if (this.updateCursorFlag) {
        this.sendMessage(JSON.stringify(this.mouseData))
        this.updateCursorFlag = false
      }
    },
    async getBoardContent () {
      try {
        const res = await getBoardContentApi(this.boardId)
        this.drawFigures(res.data.figureDtos)
      } catch (err) {
        console.log(err)
      }
    },
    async createLine () {
      return createLineApi(this.boardId)
    },
    // 畫直線
    // change line path no enter
    addLine (figure) {
      var line = new fabric.Polyline(figure.positionList, {
        id: figure.figureId,
        fill: 'transparent',
        stroke: figure.color, // 筆觸顏色
        strokeWidth: figure.strokeWidth, // 筆觸寬度
        hasControls: false, // 選中時是否可以放大縮小
        hasRotatingPoint: true, // 選中時是否可以旋轉
        hasBorders: true, // 選中時是否有邊框
        srcPoint: null,
        destPoint: null
        // evented: false  // false means event on line can't be triggered
      })
      console.log(line)
      this.canvas.add(
        line
      )
      const _this = this
      let i = 0
      line.srcPoint = this.makeDarkCircle(line.points[i].x, line.points[i].y, null, line, line.get('id'))
      for (i = 1; i < figure.positionList.length - 1; i++) {
        _this.canvas.add(
          _this.makeLightCircle(line.points[i].x, line.points[i].y, line, line, line.get('id'))
        )
        i++
        if (i === figure.positionList.length - 1) {
          break
        }
        _this.canvas.add(
          _this.makeDarkCircle(line.points[i].x, line.points[i].y, line, line, line.get('id'))
        )
      }
      line.destPoint = this.makeDarkCircle(line.points[i].x, line.points[i].y, line, null, line.get('id'))
      // console.log(line.srcPoint, line.destPoint)
      this.canvas.add(
        line.srcPoint,
        line.destPoint
      )
      this.associationMap.push({
        key: line,
        value: figure.attachedTextFigureIdList
      })
      this.canvas.renderAll()
    },
    // 畫球
    makeDarkCircle (left, top, line1, line2, lineId) {
      var circlePoint = new fabric.Circle({
        left: left,
        top: top,
        radius: 5,
        stroke: 'blue',
        fill: 'blue',
        originX: 'center',
        originY: 'center',
        xOffset: 0.0,
        yOffset: 0.0
      })
      circlePoint.attachedLineId = lineId
      circlePoint.hasControls = circlePoint.hasBorders = false
      circlePoint.line1 = line1
      circlePoint.line2 = line2

      return circlePoint
    },
    makeLightCircle (left, top, line1, line2, lineId) {
      var circlePoint = new fabric.Circle({
        left: left,
        top: top,
        radius: 10,
        borderColor: 'red',
        borderScaleFactor: 10,
        fill: 'red',
        stroke: 'red',
        originX: 'center',
        originY: 'center',
        xOffset: 0.0,
        yOffset: 0.0
      })
      circlePoint.attachedLineId = lineId
      circlePoint.hasControls = circlePoint.hasBorders = false
      circlePoint.line1 = line1
      circlePoint.line2 = line2

      return circlePoint
    },
    async createStickyNote () {
      try {
        const res = await createStickyNoteApi(this.boardId)
        console.log(res.data.message)
      } catch (err) {
        console.log(err)
      }
    },
    async changeStickyNoteContent (figure) {
      try {
        const res = await changeStickyNoteContentApi(this.boardId, figure)
        console.log(res.data.message)
      } catch (err) {
        console.log(err)
      }
    },
    async changeStickyNoteColor (figure) {
      try {
        const res = await changeStickyNoteColorApi(this.boardId, figure)
        console.log(res.data.message)
      } catch (err) {
        console.log(err)
      }
    },
    async resizeStickyNote (figure) {
      try {
        const res = await resizeStickyNoteApi(this.boardId, figure)
        console.log(res.data.message)
      } catch (err) {
        console.log(err)
      }
    },
    async moveStickyNote (figure) {
      try {
        const res = await moveStickyNoteApi(this.boardId, figure)
        console.log(res.data.message)
      } catch (err) {
        console.log(err)
      }
    },
    async deleteStickyNote (figure) {
      try {
        const res = await deleteStickyNoteApi(this.boardId, figure)
        this.canvas.remove(figure)
        console.log(res.data.message)
      } catch (err) {
        console.log(err)
      }
    },
    async changeFigureOrder () {
      try {
        const objects = this.canvas.getObjects()
        const figureList = []
        for (let i = 0; i < objects.length; i++) {
          if (objects[i].get('objectType') !== 'cursor') {
            figureList.push(objects[i].get('id'))
          }
        }
        const res = await changeFigureOrderApi(this.boardId, figureList)
        console.log(res.data.message)
      } catch (err) {
        console.log(err)
      }
    },
    initCanvas () {
      const width = Math.max(document.documentElement.clientWidth, window.innerWidth || 0)
      const height = Math.max(document.documentElement.clientHeight, window.innerHeight || 0)
      this.canvas = new fabric.Canvas('canvas', {
        width: width,
        height: height,
        fireRightClick: true, // <-- enable firing of right click events
        stopContextMenu: true, // <--  prevent context menu from showing
        freeDrawingCursor: 'none'
      })
      fabric.Object.prototype.objectType = 'normalObject'
    },
    drawFigures (figureDtos) {
      var _this = this
      figureDtos.forEach(figure => {
        if (figure.kind === 'LINE') {
          _this.addLine(figure)
        } else if (figure.kind === 'STICKYNOTE') {
          _this.addStickyNote(figure)
        }
      })
      this.associationMap.forEach(lineItem => {
        lineItem.value.forEach(attachedTextFigureId => {
          console.log(attachedTextFigureId)
          _this.canvas.getObjects().filter(object => object.id === attachedTextFigureId).forEach(item => {
            console.log('sObject', item)
            if (item.intersectsWithObject(lineItem.key.srcPoint) || item.intersectsWithObject(lineItem.key.destPoint)) {
              item.attachPoint = item.intersectsWithObject(lineItem.key.srcPoint) ? lineItem.key.srcPoint : lineItem.key.destPoint
              item.attachPoint.xOffset = (item.attachPoint.get('left') - item.get('left')) / item.width
              item.attachPoint.yOffset = (item.attachPoint.get('top') - item.get('top')) / item.height
            }
            // item.attachPoint = lineItem.key.srcPoint === null ? lineItem.key.destPoint : lineItem.key.srcPoint
            // console.log(lineItem.key.srcPoint, lineItem.key.destPoint)
          }
          )
        })
      }

      )
    },
    addStickyNote (figure) {
      var shadow = new fabric.Shadow({
        color: 'gray',
        blur: 8
      })
      var rect = new fabric.Rect({
        id: figure.figureId,
        originX: 'center',
        originY: 'center',
        width: figure.style.width,
        height: figure.style.height,
        fill: figure.style.color,
        shadow: shadow
      })
      var text = new fabric.IText(figure.content, {
        fontSize: figure.style.fontSize,
        originX: 'center',
        originY: 'center'
      }
      )
      var group = new fabric.Group([rect, text], {
        id: figure.figureId,
        content: figure.content,
        top: figure.position.y,
        left: figure.position.x,
        subTargetCheck: true
      })
      this.canvas.add(group)
      this.canvas.renderAll()
    },
    listenEventsOnCanvas () {
      var _this = this
      // listen event on context menu
      _this.addListenerOfChangeTextFigureColor()
      _this.addListenerOfDeleteTextFigure()
      _this.addListenerOfBringToFront()
      _this.addListenerOfBringForward()
      _this.addListenerOfSendBackward()
      _this.addListenerOfSendToBack()
      // listen event on canvas
      _this.listenToMouseUp()
      _this.listenToMouseMove()
      _this.listenToMouseDown()
      _this.listenToObjectScaled()
      _this.listenToObjectMoved()
      _this.listenToObjectMoving()
      _this.listenToMouseDoubleClick()
    },
    listenToMouseDoubleClick () {
      var _this = this
      _this.canvas.on(
        {
          'mouse:dblclick': function (e) {
            if (e.target != null) {
              var oldleft = e.target.left
              var oldtop = e.target.top
              var rect = e.target.item(0)
              var dimensionText = e.target.item(1)
              _this.ungroup(e.target)
              _this.canvas.setActiveObject(dimensionText)
              dimensionText.enterEditing()
              dimensionText.selectAll()
              dimensionText.on('editing:exited', function () {
                var group = new fabric.Group([rect, dimensionText], {
                  content: dimensionText.text,
                  id: rect.id,
                  left: oldleft,
                  top: oldtop
                })
                _this.canvas.remove(rect)
                _this.canvas.remove(dimensionText)
                _this.canvas.add(group)
                _this.changeStickyNoteContent(group)
              })
            }
          }
        })
    },
    listenToMouseMove () {
      var _this = this
      _this.canvas.on(
        {
          'mouse:move': function (e) {
            var mouse = this.getPointer(e)
            _this.mouseData = {
              message: {
                event: 'CursorMovedDomainEvent',
                info: {
                  userId: _this.myUserId,
                  boardId: _this.boardId,
                  position: {
                    x: mouse.x,
                    y: mouse.y
                  }
                }
              }
            }
            _this.sendMouseData()
          }
        })
    },
    listenToMouseDown () {
      var _this = this
      _this.canvas.on(
        {
          'mouse:down': function (e) {
            if (e.target != null && e.button === 3) {
              _this.canvas.setActiveObject(e.target) // right click
              _this.activeObjects = _this.canvas.getActiveObjects()
              _this.showContextMenu(e)
            // else if (e.target === null && e.button === 1) {
            //   // 點畫布的時候才畫線
            //   var pointer = this.getPointer(e)
            //   var positionX = pointer.x
            //   var positionY = pointer.y
            //   // console.log(positionX, positionY)
            //   var circlePoint = new fabric.Circle({
            //     radius: 5,
            //     fill: 'blue',
            //     left: positionX,
            //     top: positionY,
            //     selectable: true,
            //     originX: 'center',
            //     originY: 'center',
            //     hoverCursor: 'auto'
            //   })
            //   _this.linelist.push(circlePoint)
            //   if (_this.linelist.length > 1) {
            //     var startPoint = _this.linelist[0]
            //     var endPoint = _this.linelist[1]
            //     console.log(startPoint, endPoint)
            //     var lineFigure = new fabric.Line(
            //       [
            //         startPoint.get('left'),
            //         startPoint.get('top'),
            //         endPoint.get('left'),
            //         endPoint.get('top')
            //       ],
            //       {
            //         stroke: 'blue',
            //         strokeWidth: 4,
            //         hasControls: false,
            //         hasBorders: false,
            //         selectable: false,
            //         lockMovementX: true,
            //         lockMovementY: true,
            //         hoverCursor: 'default',
            //         originX: 'center',
            //         originY: 'center'
            //       }
            //     )
            //     _this.createLine(lineFigure)
            //     _this.linelist.length = 0 // clear linelist
            //   }
            } else {
              _this.hideContextMenu()
            }
          }
        }
      )
    },
    listenToMouseUp () {
      var _this = this
      _this.canvas.on(
        {
          'mouse:up': function (e) {
            if (e.target && e.target.type === 'circle') {
              _this.canvas.getObjects().some(function (item) {
                if (item.type === 'group' && e.target.intersectsWithObject(item)) {
                  console.log('mouse:up', item)
                  item.attachPoint = e.target // stickynote attribure
                  e.target.xOffset = (e.target.get('left') - item.get('left')) / item.width
                  e.target.yOffset = (e.target.get('top') - item.get('top')) / item.height
                  console.log(e.target)
                  const line = e.target.line1 ? e.target.line1 : e.target.line2
                  console.log('line:', line)
                  if (line) {
                    attachTextfigure(_this.boardId, line.get('id'), item.get('id'))
                    return true
                  }
                }
              })
            } else {
              _this.hideContextMenu()
            }
          }
        })
    },
    listenToObjectScaled () {
      var _this = this
      _this.canvas.on(
        {
          'object:scaled': function (e) {
            if (e.target.type === 'group') {
              _this.resizeStickyNote(e.target)
            }
          }
        })
    },
    listenToObjectMoving () {
      var _this = this
      _this.canvas.on(
        {
          'object:moving': function (e) {
            // todo :object typeing checking : group(StickyNote vs Line)
            if (e.target.type === 'group') {
              if (_this.updateFigureFlag) {
                _this.moveStickyNote(e.target)
                _this.updateFigureFlag = false
                if (e.target.attachPoint !== undefined) {
                  var attachPoint = e.target.attachPoint
                  const newPositionX = e.target.get('left') + attachPoint.xOffset * e.target.width
                  const newPositionY = e.target.get('top') + attachPoint.yOffset * e.target.height
                  attachPoint.set('left', newPositionX)
                  attachPoint.set('top', newPositionY)
                  _this.canvas.fire('object:moved', { target: attachPoint })
                }
              }
            }
          }
        })
    },
    listenToObjectMoved () {
      var _this = this
      _this.canvas.on(
        {
          'object:moved': function (e) {
            if (e.target.type === 'circle') {
              const p = e.target // circle
              // 整條線移動
              if (p.line2 && p.line1) {
                p.line1.points[1].x = p.left
                p.line1.points[1].y = p.top
                p.line2.points[1].x = p.left
                p.line2.points[1].y = p.top
                setTimeout(function () {
                  console.log('object:moved=', p.line2)
                  changeLinePath(_this.boardId, p.line2)
                }, 100)
              }
              if (p.line1 && !p.line2) {
                p.line1.points[2].x = p.left
                p.line1.points[2].y = p.top
                // p.line1.set({ x2: p.left, y2: p.top })
                setTimeout(function () {
                  changeLinePath(_this.boardId, p.line1)
                  console.log(p.line1)
                }, 100)
              }
              if (p.line2 && !p.line1) {
                p.line2.points[0].x = p.left
                p.line2.points[0].y = p.top
                // p.line2.set({ x1: p.left, y1: p.top })
                setTimeout(function () {
                  console.log(p.line2)
                  changeLinePath(_this.boardId, p.line2)
                }, 100)
              }
              _this.canvas.renderAll()
            }
          }
        }
      )
    },
    ungroup (group) {
      var _this = this
      var items = group._objects
      group._restoreObjectsState()
      _this.canvas.remove(group)
      for (var i = 0; i < items.length; i++) {
        _this.canvas.add(items[i])
      }
      _this.canvas.renderAll()
    },
    showContextMenu (event) {
      this.contextMenu.style.display = 'block'
      this.contextMenu.style.left = event.e.clientX + 'px'
      this.contextMenu.style.top = event.e.clientY + 'px'
      this.favcolor.value = this.canvas.getActiveObject().item(0).get('fill') // it is for group
    },
    hideContextMenu () {
      this.contextMenu.style.display = 'none'
    },
    addListenerOfChangeTextFigureColor () {
      var _this = this
      var newHandler = function () {
        _this.activeObjects.forEach((target) => {
          target.item(0).set('fill', _this.favcolor.value) // rect fill
          _this.changeStickyNoteColor(target)
        })
        _this.hideContextMenu()
      }
      _this.favcolor.addEventListener('change', newHandler)
    },
    addListenerOfDeleteTextFigure () {
      var _this = this
      var newHandler = function () {
        _this.activeObjects.forEach((target) => {
          if (target.selectable) {
            if (target.type === 'group') {
              _this.deleteStickyNote(target)
            } else {
              deleteLine(_this.boardId, target)
            }
          }
        })
        _this.hideContextMenu()
      }
      _this.delButton.addEventListener('mouseup', newHandler)
    },
    addListenerOfBringToFront () {
      var _this = this
      var newHandler = function () {
        _this.activeObjects.forEach((target) => {
          target.bringToFront()
          _this.changeFigureOrder()
        })
        _this.hideContextMenu()
      }
      _this.bringToFrontButton.addEventListener('mouseup', newHandler)
    },
    addListenerOfBringForward () {
      var _this = this
      var newHandler = function () {
        _this.activeObjects.forEach((target) => {
          if (target.selectable) {
            target.bringForward()
            _this.changeFigureOrder()
          }
        })
        _this.hideContextMenu()
      }
      _this.bringForwardButton.addEventListener('mouseup', newHandler)
    },
    addListenerOfSendBackward () {
      var _this = this
      var newHandler = function () {
        _this.activeObjects.forEach((target) => {
          target.sendBackwards()
          _this.changeFigureOrder()
        })
        _this.hideContextMenu()
      }
      _this.sendBackwardButton.addEventListener('mouseup', newHandler)
    },
    addListenerOfSendToBack () {
      var _this = this
      var newHandler = function () {
        _this.activeObjects.forEach((target) => {
          target.sendToBack()
          _this.changeFigureOrder()
        })
        _this.hideContextMenu()
      }
      _this.sendToBackButton.addEventListener('mouseup', newHandler)
    },
    updateStickyNotePosition (figure) { // todo: update userCursorList
      try {
        this.canvas.getObjects().forEach(function (item) {
          if (item.get('id') === figure.figureId) {
            item.set('left', figure.newPosition.x)
            item.set('top', figure.newPosition.y)
          }
        })
        this.canvas.renderAll()
      } catch (e) {
        console.log(e)
      }
    },
    updateStickyNoteSize (figure) {
      try {
        this.canvas.getObjects().forEach(function (group) {
          if (group.get('id') === figure.figureId) {
            group.item(0).set('width', figure.newWidth)// rect
            group.item(0).set('height', figure.newHeight)
            group.set('width', figure.newWidth)
            group.set('height', figure.newHeight)
            group.set('scaleX', 1)
            group.set('scaleY', 1)
            // set scale as 1 to using absolute size
          }
        })
        this.canvas.renderAll()
      } catch (e) {
        console.log(e)
      }
    },
    addUserCursor (data) {
      const left = data.newPosition !== undefined ? data.newPosition.x : 0.0
      const top = data.newPosition !== undefined ? data.newPosition.y : 0.0
      try {
        var userId = data.userId
        if (userId === this.myUserId) {
          return
        }
        this.userCursorList.push(data)
        const cursor = new fabric.Text(userId, {
          fontSize: 15,
          left: left,
          top: top,
          selectable: false,
          id: userId,
          objectType: 'cursor'
        })
        this.canvas.add(cursor)
        this.canvas.renderAll()
      } catch (e) {
        console.log(e)
      }
    },
    updateUserCursor (data) { // todo: update userCursorList
      try {
        const _this = this
        let foundCursor
        this.canvas.getObjects().forEach(function (item) {
          if (data.userId !== _this.myUserId && item.get('id') === data.userId) {
            foundCursor = item
            item.set('left', data.newPosition.x)
            item.set('top', data.newPosition.y)
          }
        })
        if (foundCursor === undefined) {
          this.addUserCursor(data)
        } else {
          this.canvas.renderAll()
        }
      } catch (e) {
        console.log(e)
      }
    },
    delUserCursor (userId) {
      try {
        const _this = this
        const cursorObject = this.canvas.getObjects().filter(object => object.id === userId)[0]
        this.canvas.remove(cursorObject)
        for (var i = 0; i < _this.userCursorList.length; i++) {
          if (_this.userCursorList[i][0] === userId) {
            _this.userCursorList.splice(i, 1)
            break
          }
        }
      } catch (e) {
        console.log(e)
      }
    },
    websocketonopen: function () {
      console.log('WebSocket连接成功')
    },
    websocketonerror: function () {
      console.log('WebSocket连接发生错误')
    },
    websocketonmessage: function (e) {
      const _this = this
      const receivedData = JSON.parse(e.data)
      // console.log(receivedData)
      switch (receivedData.event) {
        case 'CursorMovedDomainEvent':
          _this.updateUserCursor(receivedData)
          break
        case 'BoardEnteredDomainEvent':
          // console.log(receivedData)
          break
        case 'CursorCreatedDomainEvent':
          _this.addUserCursor(receivedData)
          this.updateCursorFlag = true
          this.sendMouseData()
          break
        case 'CursorDeletedDomainEvent':
          // console.log(receivedData)
          _this.delUserCursor(receivedData.userId)
          break
        case 'StickyNoteCreatedDomainEvent':
          var stickyNote = {
            figureId: receivedData.figureId,
            content: '',
            position: {
              x: 100,
              y: 100
            },
            style: {
              fontSize: 20,
              shape: 2,
              width: 150.0,
              height: 150.0,
              color: '#ffa150'
            }
          }
          _this.addStickyNote(stickyNote)
          break
        case 'StickyNoteDeletedDomainEvent':
          try {
            const cursorObject = this.canvas.getObjects().filter(object => object.id === receivedData.figureId)[0]
            this.canvas.remove(cursorObject)
          } catch (e) {
            console.log(e)
          }
          break
        case 'StickyNoteResizedDomainEvent':
          // console.log(receivedData)
          _this.updateStickyNoteSize(receivedData)
          break
        case 'StickyNoteColorChangedDomainEvent':
          break
        case 'StickyNoteMovedDomainEvent':
          _this.updateStickyNotePosition(receivedData)
          break
        case 'LineCreatedDomainEvent':
          var line = {
            figureId: receivedData.figureId,
            positionList: [
              {
                x: 100.0,
                y: 100.0
              }, {
                x: 200.0,
                y: 200.0
              }, {
                x: 300.0,
                y: 300.0
              }
            ],
            strokeWidth: 5,
            color: '#000000'
          }
          _this.addLine(line)
          break
        case 'LineDeletedDomainEvent':
          try {
            const lineObject0 = this.canvas.getObjects().filter(object => object.id === receivedData.figureId)[0]
            const endPointObjects = this.canvas.getObjects().filter(object => object.attachedLineId === receivedData.figureId)
            console.log('endPointObjects: ', endPointObjects)
            this.canvas.remove(lineObject0)
            this.canvas.remove(endPointObjects[0])
            this.canvas.remove(endPointObjects[1])
          } catch (e) {
            console.log(e)
          }
          break
      }
    },
    websocketclose: function (e) {
      console.log('connection closed ()')
    },
    socketInit () {
      this.myUserId = uuidGenerator.generateUUID()
      this.socket = new WebSocket(`${webSocketHostIp}/websocket/${this.boardId}/${this.myUserId}/`)
    },
    sendMessage: function (data) {
      if (this.socket.readyState === 1) {
        this.socket.send(data)
      }
    }
  }

}
</script>
