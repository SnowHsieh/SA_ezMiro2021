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
import { createLineApi, deleteLineApi } from '../apis/lineApi'
export default {
  data () {
    return {
      boardId: 'f51edf13-a8fa-431a-859a-836b2a7b2c22',
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
      updateFigureFlag: true
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
    async createLine (figure) {
      try {
        const res = await createLineApi(this.boardId, figure)
        console.log(res.data.message)
      } catch (err) {
        console.log(err)
      }
    },
    async deleteLine (figure) {
      try {
        const res = await deleteLineApi(this.boardId, figure)
        console.log(res.data.message)
      } catch (err) {
        console.log(err)
      }
    },
    // 畫直線
    addLine (figure) {
      const srcPosition = figure.positionList[0]
      const destPosition = figure.positionList[1]
      const line = new fabric.Line([srcPosition.x, srcPosition.y, destPosition.x, destPosition.y], {
        id: figure.figureId,
        // fill: figure.color,
        stroke: figure.color, // 筆觸顏色
        strokeWidth: figure.strokeWidth, // 筆觸寬度
        hasControls: true, // 選中時是否可以放大縮小
        hasRotatingPoint: true, // 選中時是否可以旋轉
        hasBorders: true // 選中時是否有邊框
        // evented: false  // false means event on line can't be triggered
      })
      console.log('line: ', line)
      // line.set({ x2: 250.0, y2: 200.0 })
      // line.set({ x1: 0.0, y1: 20.0 })
      // const point1 = this.makeCircle(line.x2, line.y2, line, null)
      // const point2 = this.makeCircle(line.x1, line.y1, null, line)
      // line.point1 = point1
      // line.point2 = point2
      this.canvas.add(line)
      this.canvas.add(
        this.makeCircle(line.x2, line.y2, line, null, line.get('id')),
        this.makeCircle(line.x1, line.y1, null, line, line.get('id'))
      )
      this.canvas.renderAll()
      // return line
    },
    // 畫球
    makeCircle (left, top, line1, line2, lineId) {
      var c = new fabric.Circle({
        left: left,
        top: top,
        strokeWidth: 5,
        radius: 5,
        fill: '#f9f900',
        stroke: '#f9f900',
        originX: 'center',
        originY: 'center'
      })
      c.attachedLineId = lineId
      c.hasControls = c.hasBorders = false
      c.line1 = line1
      c.line2 = line2

      return c
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
          console.log(figure)
        } else if (figure.kind === 'STICKYNOTE') {
          _this.addStickyNote(figure)
        }
      })
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
      _this.listenToMouseMove()
      _this.listenToMouseDown()
      _this.listenToObjectScaled()
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
            if (e.target.type === 'group') {
              if (_this.updateFigureFlag) {
                _this.moveStickyNote(e.target)
                _this.updateFigureFlag = false
              }
            } else {
              const p = e.target
              p.line1 && p.line1.set({ x2: p.left, y2: p.top })
              p.line2 && p.line2.set({ x1: p.left, y1: p.top })
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
              _this.deleteLine(target)
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
      if (receivedData.event === 'CursorMovedDomainEvent') {
        _this.updateUserCursor(receivedData)
      } else if (receivedData.event === 'BoardEnteredDomainEvent') {
        // console.log(receivedData)
      } else if (receivedData.event === 'CursorCreatedDomainEvent') {
        _this.addUserCursor(receivedData)
        this.updateCursorFlag = true
        this.sendMouseData()
      } else if (receivedData.event === 'CursorDeletedDomainEvent') {
        // console.log(receivedData)
        _this.delUserCursor(receivedData.userId)
      } else if (receivedData.event === 'StickyNoteCreatedDomainEvent') {
        const figure = {
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
        _this.addStickyNote(figure)
      } else if (receivedData.event === 'StickyNoteDeletedDomainEvent') {
        try {
          const cursorObject = this.canvas.getObjects().filter(object => object.id === receivedData.figureId)[0]
          this.canvas.remove(cursorObject)
        } catch (e) {
          console.log(e)
        }
      } else if (receivedData.event === 'StickyNoteResizedDomainEvent') {
        // console.log(receivedData)
        _this.updateStickyNoteSize(receivedData)
      } else if (receivedData.event === 'StickyNoteColorChangedDomainEvent') {

      } else if (receivedData.event === 'StickyNoteMovedDomainEvent') {
        _this.updateStickyNotePosition(receivedData)
      } else if (receivedData.event === 'LineCreatedDomainEvent') {
        const figure = {
          positionList: [
            {
              x: 100.0,
              y: 100.0
            }, {
              x: 250.0,
              y: 200.0
            }
          ],
          strokeWidth: 5,
          color: '#000000'
        }
        _this.addLine(figure)
      } else if (receivedData.event === 'LineDeletedDomainEvent') {
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
