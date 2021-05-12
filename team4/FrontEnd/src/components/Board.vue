<!--<script src="../assets/js/jquery.colorPicker.js"></script>-->
<template>
        <!-- <button @click="drawARect">畫圖</button> -->
        <div>
          <div>
            <button id="createStickyNoteButton" @click="createStickyNote()">Add New StickyNote</button>
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
import io from 'socket.io-client'
import axios from 'axios'
export default {

  data () {
    return {
      boardId: null,
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
      myUserId: '你沒ip QQ',
      hostIp: '140.124.181.9'
    }
  },
  created () {
    this.socket = io('http://' + this.hostIp + ':4040', { transports: ['websocket'] })
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
    this.socket.once('getAllUserCursors', data => {
      this.myUserId = JSON.parse(data).id
      this.userCursorList = JSON.parse(data).userCursorMap
      this.drawAllUserCursors()
    })
    this.socket.on('userJoin', data => {
      this.addUserCursor(data)
    })
    this.socket.on('userCursorUpdate', data => {
      this.updateUserCursor(data)
    })
    this.socket.on('userLeft', userId => {
      this.delUserCursor(userId)
    })
    this.listenEventsOnCanvas()
    // this.timer = setInterval(this.refreshCanvas, 10000)
  },
  methods: {
    async getBoardContent () {
      try {
        this.boardId = '44b608e4-781b-47c5-9034-a8c89430b1e4'
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
        _this.canvas.add(group)
      })
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
            var data = {
              id: _this.myUserId,
              position: {
                x: mouse.x,
                y: mouse.y
              }
            }
            _this.socket.emit('mouse-moved', data)
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
    drawAllUserCursors () {
      var _this = this
      this.userCursorList.forEach(function (item) {
        const userId = item[0]
        const position = item[1]
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
        var userId = data.id
        if (userId === this.myUserId) {
          return
        }
        var position = data.position
        this.userCursorList.push(data)
        const cursor = new fabric.Text(userId, {
          fontSize: 15,
          left: position.x,
          top: position.y,
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
          if (data.id !== _this.myUserId && item.get('id') === data.id) {
            item.set('left', data.position.x)
            item.set('top', data.position.y)
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
    }
  }

}
</script>
