<!--<script src="../assets/js/jquery.colorPicker.js"></script>-->
<template>
        <!-- <button @click="drawARect">畫圖</button> -->
        <div>
          <div>
            <button id="createStickyNoteButton" @click="createStickyNote()">Add New StickyNote</button>
            <input v-model="myUserId" placeholder="input userName">
            <button id="sendUserName" @click="socketInit()">sendUserName</button>
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
// import io from 'socket.io-client'
import axios from 'axios'
export default {
  data () {
    return {
      boardId: '7cb48574-886a-4ba9-8f92-67ba9abb33c9',
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
      myUserId: '7398cd26-da85-4c05-b04b-122e73888dfb',
      hostIp: '140.124.181.8',
      mouseData: null
    }
  },
  created () {
    this.socketInit()
  },
  destroyed: function () { // 离开页面生命周期函数
    this.websocketclose()
  },
  async mounted () {
    this.boardContent = this.getBoardContent()
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
    // this.timer = setInterval(this.refreshCanvas, 10000)
    this.timer = setInterval(this.sendMouseData, 500)
  },
  methods: {
    sendMouseData () {
      // console.log(this.mouseData)
      this.sendMessage(JSON.stringify(this.mouseData))
    },
    async getBoardContent () {
      try {
        const res = await axios.get('http://' + this.hostIp + ':8081/boards/' + this.boardId + '/content')
        this.drawStickyNote(res.data.figureDtos)
      } catch (err) {
        console.log(err)
      }
    },
    async createStickyNote () {
      try {
        const res = await axios.post('http://' + this.hostIp + ':8081/board/' + this.boardId + '/createStickyNote',
          {
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
        )
        console.log(res.data.message)
      } catch (err) {
        console.log(err)
      }
      this.refreshCanvas()
    },
    async changeStickyNoteContent (figure) {
      try {
        const res = await axios.post('http://' + this.hostIp + ':8081/board/' + this.boardId + '/changeStickyNoteContent',
          {
            figureId: figure.get('id'),
            content: figure.item(1).get('text')
          }
        )
        console.log(res.data.message)
      } catch (err) {
        console.log(err)
      }
      this.refreshCanvas()
    },
    async changeStickyNoteColor (figure) {
      try {
        const res = await axios.post('http://' + this.hostIp + ':8081/board/' + this.boardId + '/changeStickyNoteColor',
          {
            figureId: figure.get('id'),
            color: figure.item(0).get('fill')
          }
        )
        console.log(res.data.message)
      } catch (err) {
        console.log(err)
      }
      this.refreshCanvas()
    },
    async resizeStickyNote (figure) {
      try {
        const res = await axios.post('http://' + this.hostIp + ':8081/board/' + this.boardId + '/resizeStickyNote',
          {
            figureId: figure.get('id'),
            width: parseFloat(figure.width) * parseFloat(figure.get('scaleX')),
            height: parseFloat(figure.height) * parseFloat(figure.get('scaleY'))
          }
        )
        console.log(res.data.message)
      } catch (err) {
        console.log(err)
      }
      this.refreshCanvas()
    },
    async moveStickyNote (figure) {
      try {
        const res = await axios.post('http://' + this.hostIp + ':8081/board/' + this.boardId + '/moveStickyNote',
          {
            figureId: figure.get('id'),
            top: figure.get('top'),
            left: figure.get('left')
          }
        )
        console.log(res.data.message)
      } catch (err) {
        console.log(err)
      }
      // this.refreshCanvas()
    },
    async deleteStickyNote (figure) {
      try {
        const res = await axios.post('http://' + this.hostIp + ':8081/board/' + this.boardId + '/deleteStickyNote',
          {
            figureId: figure.get('id')
          }
        )
        this.canvas.remove(figure)
        console.log(res.data.message)
      } catch (err) {
        console.log(err)
      }
      // this.refreshCanvas()
    },
    async changeFigureOrder () {
      try {
        var objects = this.canvas.getObjects()
        var flist = []
        for (var i = 0; i < objects.length; i++) {
          if (objects[i].get('objectType') !== 'cursor') {
            flist.push(objects[i].get('id'))
          }
        }
        const res = await axios.post('http://' + this.hostIp + ':8081/boards/' + this.boardId + '/changeFigureOrder',
          {
            figureOrderList: flist
          }
        )
        console.log(res.data.message)
      } catch (err) {
        console.log(err)
      }
    },
    initCanvas () {
      var width = Math.max(document.documentElement.clientWidth, window.innerWidth || 0)
      var height = Math.max(document.documentElement.clientHeight, window.innerHeight || 0)
      this.canvas = new fabric.Canvas('canvas', {
        width: width,
        height: height,
        fireRightClick: true, // <-- enable firing of right click events
        stopContextMenu: true, // <--  prevent context menu from showing
        freeDrawingCursor: 'none'
      })
      fabric.Object.prototype.objectType = 'normalObject'
    },
    drawStickyNote (figureDtos) {
      var _this = this
      figureDtos.forEach(figure => {
        _this.addStickyNote(figure)
      })
      // this.canvas.renderAll()
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
    refreshCanvas () {
      this.canvas.clear()
      this.getBoardContent()
      this.drawAllUserCursors()
    },
    listenEventsOnCanvas () {
      var _this = this
      _this.addListenerOfChangeTextFigureColor()
      _this.addListenerOfDeleteTextFigure()
      _this.addListenerOfBringToFront()
      _this.addListenerOfBringForward()
      _this.addListenerOfSendBackward()
      _this.addListenerOfSendToBack()
      var canvas = this.canvas
      canvas.on(
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
            // _this.sendMessage(JSON.stringify(data))
            // _this.socket.emit('mouse-moved', data)
          },
          'mouse:dblclick': function (e) {
            if (e.target != null) {
              var oldleft = e.target.left
              var oldtop = e.target.top
              var rect = e.target.item(0)
              var dimensionText = e.target.item(1)
              _this.ungroup(e.target)
              canvas.setActiveObject(dimensionText)
              dimensionText.enterEditing()
              dimensionText.selectAll()
              dimensionText.on('editing:exited', function () {
                var group = new fabric.Group([rect, dimensionText], {
                  content: dimensionText.text,
                  id: rect.id,
                  left: oldleft,
                  top: oldtop
                })
                canvas.remove(rect)
                canvas.remove(dimensionText)
                canvas.add(group)
                _this.changeStickyNoteContent(group)
              })
            }
          },
          'mouse:down': function (e) {
            if (e.target != null && e.button === 3) {
              canvas.setActiveObject(e.target) // right click
              _this.activeObjects = canvas.getActiveObjects()
              _this.showContextMenu(e)
            } else {
              _this.hideContextMenu()
            }
          },
          'object:scaled': function (e) {
            if (e.target.type === 'group') {
              _this.resizeStickyNote(e.target)
              _this.moveStickyNote(e.target)
            }
          },
          'object:moved': function (e) {
            if (e.target.type === 'group') {
              _this.moveStickyNote(e.target)
            } else {
              e.target._objects.forEach((target) => {
                _this.moveStickyNote(target)
              })
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
      this.favcolor.value = this.canvas.getActiveObject().item(0).get('fill')
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
            _this.deleteStickyNote(target)
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
    updateStickyPosition (figure) { // todo: update userCursorList
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
    drawAllUserCursors () {
      var _this = this
      this.userCursorList.forEach(function (item) {
        const userId = item.userId
        const position = item.position
        if (userId === _this.myUserId) {
          return
        }
        const cursor = new fabric.Text(userId, {
          fontSize: 15,
          left: position.x,
          top: position.y,
          selectable: false,
          id: userId,
          objectType: 'cursor'
        })
        _this.canvas.add(cursor)
      })
    },
    addUserCursor (data) {
      try {
        var userId = data.userId
        if (userId === this.myUserId) {
          return
        }
        this.userCursorList.push(data)
        const cursor = new fabric.Text(userId, {
          fontSize: 15,
          left: 0.0,
          top: 0.0,
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
        this.canvas.getObjects().forEach(function (item) {
          if (data.userId !== _this.myUserId && item.get('id') === data.userId) {
            item.set('left', data.newPosition.x)
            item.set('top', data.newPosition.y)
          }
        })
        this.canvas.renderAll()
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
      this.sendGetAllCursors()
    },
    websocketonerror: function () {
      console.log('WebSocket连接发生错误')
    },
    websocketonmessage: function (e) {
      const _this = this
      const receivedData = JSON.parse(e.data)
      if (receivedData.event === 'CursorMovedDomainEvent') {
        // console.log(receivedData)
        _this.updateUserCursor(receivedData)
      } else if (receivedData.event === 'BoardEnteredDomainEvent') {
        // console.log(receivedData)
      } else if (receivedData.event === 'CursorCreatedDomainEvent') {
        console.log(receivedData)
        _this.addUserCursor(receivedData)
      } else if (receivedData.event === 'CursorDeletedDomainEvent') {
        console.log(receivedData)
        _this.delUserCursor(receivedData.userId)
      } else if (receivedData.event === 'StickyNoteCreatedDomainEvent') {
        console.log(receivedData)
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
      } else if (receivedData.event === 'StickyNoteMovedDomainEvent') {
        console.log(receivedData)
        _this.updateStickyPosition(receivedData)
      } else if (receivedData.event === 'GetAllCursorList') {
        _this.userCursorList = receivedData.cursorList.cursorDtos
        _this.drawAllUserCursors()
      }
      // if (JSON.parse(e.data).event === 'CursorCreatedDomainEvent') {
      //   console.log(JSON.parse(e.data))

      // if (JSON.parse(e.data).event === 'CursorCreatedDomainEvent') {
      //   console.log(JSON.parse(e.data))
      // }
      // console.log('收到來自後端的訊息2', e.message.event)
      // console.log('收到來自後端的訊息3', e.message.info)
      // console.log('收到來自後端的訊息4', e.message.position)
      // console.log(JSON.parse(e.data)) // console.log(e);
    },
    websocketclose: function (e) {
      console.log('connection closed ()')
    },
    socketInit () {
      // this.socket = new WebSocket('ws://' + this.hostIp + ':8081/websocket/' + this.myUserId + '/')
      this.myUserId = this.generateUUID()
      this.socket = new WebSocket('ws://' + this.hostIp + ':8081/websocket/' + this.boardId + '/' + this.myUserId + '/')
    },
    sendGetAllCursors () {
      if (this.socket.readyState === 1) {
        console.log('sendGetAllCursors')
        const data = {
          message: {
            event: 'getAllUser',
            info: {
              boardId: this.boardId
            }
          }
        }
        this.socket.send(JSON.stringify(data))
      }
    },
    sendMessage: function (data) {
      // console.log('sendMsg:', data)
      if (this.socket.readyState === 1) {
        this.socket.send(data)
      }
    },
    generateUUID () {
      var d = Date.now()
      if (typeof performance !== 'undefined' && typeof performance.now === 'function') {
        d += performance.now() // use high-precision timer if available
      }
      return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        var r = (d + Math.random() * 16) % 16 | 0
        d = Math.floor(d / 16)
        return (c === 'x' ? r : (r & 0x3 | 0x8)).toString(16)
      })
    }
  }

}
</script>
