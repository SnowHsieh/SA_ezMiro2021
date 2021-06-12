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
              <input type="color" id="pickerColor" name="pickerColor" list="colors" style="margin-left: 4rem" />
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
import { changeFigureOrder, getBoardContentApi } from '@/apis/boardApi'
import {
  createStickyNoteApi,
  changeStickyNoteContentApi,
  changeStickyNoteColorApi,
  resizeStickyNoteApi, moveStickyNoteApi, deleteStickyNoteApi
} from '@/apis/stickyNoteApi'
import { webSocketHostIp } from '../config/config.js'
import uuidGenerator from '../utils/uuidGenerator.js'
import {
  attachConnectableFigure,
  unattachConnectableFigure,
  changeLinePath,
  createLineApi,
  deleteLine
} from '@/apis/lineApi'
export default {
  data () {
    return {
      boardId: 'c06a6073-dab0-486f-849d-d30f9dcaec03',
      canvasContext: null,
      boardContent: null,
      canvas: null,
      time: 0,
      contextMenu: null,
      delButton: null,
      pickerColor: null,
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
      updateLineFlag: true,
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
    this.pickerColor = document.getElementById('pickerColor')
    this.bringToFrontButton = document.getElementById('bringToFrontButton')
    this.bringForwardButton = document.getElementById('bringForwardButton')
    this.sendBackwardButton = document.getElementById('sendBackwardButton')
    this.sendToBackButton = document.getElementById('sendToBackButton')
    this.listenEventsOnCanvas()
    this.socket.onopen = this.websocketonopen
    this.socket.onerror = this.websocketonerror
    this.socket.onmessage = this.websocketonmessage
    this.socket.onclose = this.websocketclose
    this.timer = setInterval(this.updateFlag, 300)
  },
  methods: {
    updateFlag () {
      this.updateCursorFlag = true
      this.updateFigureFlag = true
      this.updateLineFlag = true
    },
    sendMouseData () {
      if (this.updateCursorFlag) {
        this.sendMessage(JSON.stringify(this.mouseData))
        this.updateCursorFlag = false
      }
    },
    sendLinePathData (line) {
      if (this.updateLineFlag) {
        changeLinePath(this.boardId, line)
        this.updateLineFlag = false
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
        hasBorders: false, // 選中時是否有邊框
        selectable: false,
        srcPoint: null,
        destPoint: null,
        objectCaching: false
        // evented: false  // false means event on line can't be triggered
      })
      this.canvas.add(
        line
      )
      const _this = this
      for (let i = 1; i < figure.positionList.length - 1; i++) {
        _this.canvas.add(
          _this.makeCircle(line.points[i].x, line.points[i].y, line, i)
        )
      }
      // todo: refactor it with index
      line.srcPoint = this.makeDarkCircle(line.points[0].x, line.points[0].y, null, line, line.get('id'), 0)
      line.destPoint = this.makeDarkCircle(line.points[figure.positionList.length - 1].x, line.points[figure.positionList.length - 1].y, line, null, line.get('id'), figure.positionList.length - 1)

      this.canvas.add(
        line.srcPoint,
        line.destPoint
      )

      this.associationMap.push({
        key: line,
        value: {
          srcConnectableFigureId: figure.srcConnectableFigureId,
          destConnectableFigureId: figure.destConnectableFigureId
        }
      })
      this.canvas.renderAll()
    },
    // 畫球
    makeDarkCircle (left, top, line1, line2, lineId, index) {
      var circlePoint = new fabric.Circle({
        left: left,
        top: top,
        radius: 5,
        stroke: 'blue',
        fill: 'blue',
        originX: 'center',
        originY: 'center',
        xOffset: 0.0,
        yOffset: 0.0,
        index: index
      })
      circlePoint.attachedLineId = lineId
      circlePoint.hasControls = circlePoint.hasBorders = false
      circlePoint.line1 = line1
      circlePoint.line2 = line2
      circlePoint.line = !line2 ? line1 : line2

      return circlePoint
    },
    makeCircle (left, top, line, index) {
      var circlePoint = new fabric.Circle({
        left: left,
        top: top,
        radius: 10,
        borderColor: 'gray',
        borderScaleFactor: 10,
        fill: 'gray',
        stroke: 'gray',
        originX: 'center',
        originY: 'center',
        xOffset: 0.0,
        yOffset: 0.0,
        index: index
      })
      circlePoint.attachedLineId = line.get('id')
      circlePoint.hasControls = circlePoint.hasBorders = false
      circlePoint.line = line

      return circlePoint
    },
    async createStickyNote () {
      try {
        await createStickyNoteApi(this.boardId)
      } catch (err) {
        console.log(err)
      }
    },
    async changeStickyNoteContent (figure) {
      try {
        await changeStickyNoteContentApi(this.boardId, figure)
      } catch (err) {
        console.log(err)
      }
    },
    async changeStickyNoteColor (figure) {
      try {
        await changeStickyNoteColorApi(this.boardId, figure)
      } catch (err) {
        console.log(err)
      }
    },
    async resizeStickyNote (figure) {
      try {
        await resizeStickyNoteApi(this.boardId, figure)
      } catch (err) {
        console.log(err)
      }
    },
    async moveStickyNote (figure) {
      try {
        await moveStickyNoteApi(this.boardId, figure)
      } catch (err) {
        console.log(err)
      }
    },
    async deleteStickyNote (figure) {
      try {
        await deleteStickyNoteApi(this.boardId, figure)
      } catch (err) {
        console.log(err)
      }
    },
    getFigureListOnCanvas () {
      const objects = this.canvas.getObjects()
      const figureList = []
      for (let i = 0; i < objects.length; i++) {
        if (objects[i].get('objectType') !== 'cursor' && objects[i].get('id')) {
          figureList.push(objects[i].get('id'))
        }
      }
      return figureList
    },
    initCanvas () {
      // const width = Math.max(document.documentElement.clientWidth, window.innerWidth || 0)
      // const height = Math.max(document.documentElement.clientHeight, window.innerHeight || 0)
      this.canvas = new fabric.Canvas('canvas', {
        width: 15000,
        height: 5624,
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
        _this.canvas.getObjects().filter(object => object.type === 'group').forEach(stickyNoteOnCanvas => {
          if (lineItem.value.srcConnectableFigureId && lineItem.value.srcConnectableFigureId === stickyNoteOnCanvas.id) {
            lineItem.key.srcPoint.xOffset = (lineItem.key.srcPoint.get('left') - stickyNoteOnCanvas.get('left')) / stickyNoteOnCanvas.width
            lineItem.key.srcPoint.yOffset = (lineItem.key.srcPoint.get('top') - stickyNoteOnCanvas.get('top')) / stickyNoteOnCanvas.height
            stickyNoteOnCanvas.attachPointSet.add(lineItem.key.srcPoint)
          }
          if (lineItem.value.destConnectableFigureId && lineItem.value.destConnectableFigureId === stickyNoteOnCanvas.id) {
            lineItem.key.destPoint.xOffset = (lineItem.key.destPoint.get('left') - stickyNoteOnCanvas.get('left')) / stickyNoteOnCanvas.width
            lineItem.key.destPoint.yOffset = (lineItem.key.destPoint.get('top') - stickyNoteOnCanvas.get('top')) / stickyNoteOnCanvas.height
            stickyNoteOnCanvas.attachPointSet.add(lineItem.key.destPoint)
          }
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
        width: figure.width,
        height: figure.height,
        fill: figure.style.color,
        shadow: shadow
      })
      var text = new fabric.IText(figure.content, {
        fontSize: figure.style.fontSize,
        originX: 'center',
        originY: 'center'
      })
      var group = new fabric.Group([rect, text], {
        id: figure.figureId,
        content: figure.content,
        top: figure.position.y,
        left: figure.position.x,
        subTargetCheck: true,
        attachPointSet: new Set()
      })
      this.canvas.add(group)
      this.canvas.renderAll()
    },
    listenEventsOnCanvas () {
      var _this = this
      // listen event on context menu
      _this.addListenerOfChangeConnectableFigureColor()
      _this.addListenerOfDeleteFigure()
      _this.addListenerOfBringToFront()
      _this.addListenerOfBringForward()
      _this.addListenerOfSendBackward()
      _this.addListenerOfSendToBack()
      // listen event on canvas
      _this.listenToMouseUp()
      _this.listenToMouseMove()
      _this.listenToMouseDown()
      _this.listenToObjectScaled()
      _this.listenToObjectMoving()
      _this.listenToMouseDoubleClick()
      // _this.listenToMouseScrollWheel()
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
              var originalAttachPointSet = e.target.attachPointSet
              _this.ungroup(e.target)
              _this.canvas.setActiveObject(dimensionText)
              dimensionText.enterEditing()
              dimensionText.selectAll()
              dimensionText.on('editing:exited', function () {
                var group = new fabric.Group([rect, dimensionText], {
                  content: dimensionText.text,
                  id: rect.id,
                  left: oldleft,
                  top: oldtop,
                  attachPointSet: originalAttachPointSet
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
              _this.canvas.getObjects().filter(x => x.type === 'group').some(function (stickyNote) {
                const promise1 = new Promise((resolve, reject) => {
                  if (stickyNote.attachPointSet.has(e.target)) {
                    stickyNote.attachPointSet.delete(e.target)
                    const line = e.target.line1 ? e.target.line1 : e.target.line2
                    if (line.srcPoint === e.target) {
                      unattachConnectableFigure(_this.boardId, line.get('id'), 'source')
                      resolve('Success')
                    } else if (line.destPoint === e.target) {
                      unattachConnectableFigure(_this.boardId, line.get('id'), 'destination')
                      resolve('Success')
                    }
                  } else {
                    resolve('Success')
                  }
                })
                promise1.then((res) => {
                  if (e.target.intersectsWithObject(stickyNote)) {
                    stickyNote.attachPointSet.add(e.target)
                    e.target.xOffset = (e.target.get('left') - stickyNote.get('left')) / stickyNote.width
                    e.target.yOffset = (e.target.get('top') - stickyNote.get('top')) / stickyNote.height
                    const line = e.target.line1 ? e.target.line1 : e.target.line2
                    if (line.srcPoint === e.target) {
                      setTimeout(function () {
                        attachConnectableFigure(_this.boardId, line.get('id'), stickyNote.get('id'), 'source')
                      }, 500)
                      return true
                    } else if (line.destPoint === e.target) {
                      setTimeout(function () {
                        attachConnectableFigure(_this.boardId, line.get('id'), stickyNote.get('id'), 'destination')
                      }, 500)
                      return true
                    }
                  }
                })
                // solve change sn
                // 中點不能被attach
              })
            }
          }
        })
    },
    listenToMouseScrollWheel () {
      const _this = this
      _this.canvas.on('mouse:wheel', function (opt) {
        const delta = opt.e.deltaY
        let zoom = _this.canvas.getZoom()
        zoom *= 0.999 ** delta
        if (zoom > 20) zoom = 20
        if (zoom < 0.01) zoom = 0.01
        _this.canvas.zoomToPoint({ x: opt.e.offsetX, y: opt.e.offsetY }, zoom)
        opt.e.preventDefault()
        opt.e.stopPropagation()
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
                e.target.attachPointSet.forEach(function (attachPoint) {
                  const newPositionX = e.target.get('left') + attachPoint.xOffset * e.target.width
                  const newPositionY = e.target.get('top') + attachPoint.yOffset * e.target.height
                  attachPoint.set('left', newPositionX)
                  attachPoint.set('top', newPositionY)
                  _this.canvas.fire('object:moving', { target: attachPoint })
                })
              }
            } else if (e.target.type === 'circle') {
              const p = e.target // circle
              p.line.points[p.index].x = p.left
              p.line.points[p.index].y = p.top
              _this.sendLinePathData(p.line)
              _this.canvas.renderAll()
            }
          }
        })
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
      // this.contextMenu.style.left = event.e.clientX + 'px'
      // this.contextMenu.style.top = event.e.clientY + 'px'
      this.contextMenu.style.left = this.canvas.getPointer(event.e).x + 20 + 'px'
      this.contextMenu.style.top = this.canvas.getPointer(event.e).y + 20 + 'px'
      this.pickerColor.value = this.canvas.getActiveObject().item(0).get('fill') // it is for group
    },
    hideContextMenu () {
      this.contextMenu.style.display = 'none'
    },
    addListenerOfChangeConnectableFigureColor () {
      var _this = this
      var newHandler = function () {
        _this.activeObjects.forEach((target) => {
          target.item(0).set('fill', _this.pickerColor.value) // rect fill
          _this.changeStickyNoteColor(target)
        })
        _this.hideContextMenu()
      }
      _this.pickerColor.addEventListener('change', newHandler)
    },
    addListenerOfDeleteFigure () {
      var _this = this
      var newHandler = function () {
        _this.activeObjects.forEach((target) => {
          if (target.selectable) {
            if (target.type === 'group') {
              _this.deleteStickyNote(target)
            } else {
              deleteLine(_this.boardId, target.line)
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
          changeFigureOrder(_this.boardId, _this.getFigureListOnCanvas())
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
            changeFigureOrder(_this.boardId, _this.getFigureListOnCanvas())
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
          changeFigureOrder(_this.boardId, _this.getFigureListOnCanvas())
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
          changeFigureOrder(_this.boardId, _this.getFigureListOnCanvas())
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
    updateStickyNoteColor (figure) {
      try {
        this.canvas.getObjects().forEach(function (group) {
          if (group.get('id') === figure.figureId) {
            group.item(0).set('fill', figure.newColor)// rect
          }
        })
        this.canvas.renderAll()
      } catch (e) {
        console.log(e)
      }
    },
    updateStickyNoteContent (figure) {
      try {
        this.canvas.getObjects().forEach(function (group) {
          if (group.get('id') === figure.figureId) {
            group.set('content', figure.newContent)
            group.item(1).set('text', figure.newContent)
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
      console.log('WebSocket connected')
    },
    websocketonerror: function () {
      console.log('WebSocket connection error')
    },
    websocketonmessage: function (e) {
      const _this = this
      const receivedData = JSON.parse(e.data)
      switch (receivedData.event) {
        case 'CursorMovedDomainEvent':
          _this.updateUserCursor(receivedData)
          break
        case 'BoardEnteredDomainEvent':
          _this.addUserCursor(receivedData)
          this.updateCursorFlag = true
          this.sendMouseData()
          break
        case 'BoardLeftDomainEvent' :
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
            width: 150.0,
            height: 150.0,
            style: {
              fontSize: 20,
              shape: 2,
              color: '#ffa150'
            }
          }
          _this.addStickyNote(stickyNote)
          break
        case 'StickyNoteDeletedDomainEvent':
          try {
            const stickyNoteObject = this.canvas.getObjects().filter(object => object.id === receivedData.figureId)[0]
            this.canvas.remove(stickyNoteObject)
          } catch (e) {
            console.log(e)
          }
          break
        case 'StickyNoteResizedDomainEvent':
          _this.updateStickyNoteSize(receivedData)
          break
        case 'StickyNoteMovedDomainEvent':
          _this.updateStickyNotePosition(receivedData)
          break
        case 'StickyNoteColorChangedDomainEvent':
          _this.updateStickyNoteColor(receivedData)
          break
        case 'StickyNoteContentChangedDomainEvent':
          _this.updateStickyNoteContent(receivedData)
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
              }, {
                x: 400.0,
                y: 400.0
              }
            ],
            strokeWidth: 5,
            color: '#000000'
          }
          _this.addLine(line)
          break
        case 'LineDeletedDomainEvent':
          try {
            const lineObject0 = _this.canvas.getObjects().filter(object => object.id === receivedData.figureId)[0]
            const endPointObjects = _this.canvas.getObjects().filter(object => object.attachedLineId === receivedData.figureId)
            _this.canvas.remove(lineObject0)
            endPointObjects.forEach(function (endPoint) {
              _this.canvas.remove(endPoint)
            })
          } catch (e) {
            console.log(e)
          }
          break
        case 'LinePathChangedDomainEvent':
          try {
            _this.canvas.getObjects().filter(x => x.get('id') === receivedData.figureId).forEach(function (line) {
              line.set('points', receivedData.newPositionList)
              const pointObjectsOnLine = _this.canvas.getObjects().filter(object => object.attachedLineId === receivedData.figureId) // 4 points
              for (let i = 0; i < receivedData.newPositionList.length; i++) {
                const ep = pointObjectsOnLine.filter(point => point.index === i)[0]
                ep.set('left', receivedData.newPositionList[i].x)
                ep.set('top', receivedData.newPositionList[i].y)
              }
            })
            _this.canvas.renderAll()
          } catch (e) {
            console.log(e)
          }
          break
        case 'ConnectableFigureAttachedByLineDomainEvent':
          try {
            const line = _this.canvas.getObjects().filter(line => line.get('id') === receivedData.figureId)[0]
            let attachedStickyNote
            if (receivedData.srcConnectableFigureId) {
              attachedStickyNote = _this.canvas.getObjects().filter(stickyNote => stickyNote.get('id') === receivedData.srcConnectableFigureId)[0]
              attachedStickyNote.attachPointSet.add(line.srcPoint)
              line.srcPoint.xOffset = (line.srcPoint.get('left') - attachedStickyNote.get('left')) / attachedStickyNote.width
              line.srcPoint.yOffset = (line.srcPoint.get('top') - attachedStickyNote.get('top')) / attachedStickyNote.height
            } else if (receivedData.destConnectableFigureId) {
              attachedStickyNote = _this.canvas.getObjects().filter(stickyNote => stickyNote.get('id') === receivedData.destConnectableFigureId)[0]
              attachedStickyNote.attachPointSet.add(line.destPoint)
              line.destPoint.xOffset = (line.destPoint.get('left') - attachedStickyNote.get('left')) / attachedStickyNote.width
              line.destPoint.yOffset = (line.destPoint.get('top') - attachedStickyNote.get('top')) / attachedStickyNote.height
            }
            _this.canvas.renderAll()
          } catch (e) {
            console.log(e)
          }
          break
        case 'ConnectableFigureUnattachedDomainEvent':
          try {
            const line = _this.canvas.getObjects().filter(line => line.get('id') === receivedData.figureId)[0]
            let attachedStickyNote
            if (receivedData.attachEndPointKind === 'source') {
              attachedStickyNote = _this.canvas.getObjects().filter(stickyNote => stickyNote.get('id') === receivedData.connectableFigureIdToBeUnattached)[0]
              attachedStickyNote.attachPointSet.delete(line.srcPoint)
            } else if (receivedData.attachEndPointKind === 'destination') {
              attachedStickyNote = _this.canvas.getObjects().filter(stickyNote => stickyNote.get('id') === receivedData.connectableFigureIdToBeUnattached)[0]
              attachedStickyNote.attachPointSet.delete(line.destPoint)
            }
            _this.canvas.renderAll()
          } catch (e) {
            console.log(e)
          }
          break
      }
    },
    websocketclose: function (e) {
      console.log('connection closed')
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
