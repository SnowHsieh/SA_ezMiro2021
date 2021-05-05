<!--<script src="../assets/js/jquery.colorPicker.js"></script>-->
<template>
        <!-- <button @click="drawARect">畫圖</button> -->
        <div>
          <div><button id="createStickyNoteButton" @click="createStickyNote()">Add New StickyNote</button>
            <canvas id="canvas" ref='board' >
            </canvas>
          </div>

          <div class="container" oncontextmenu="return false">
            <div id="contextMenu" class="context-menu">
              <ul>
                <label>
                  <li>ColorPicker
                    <input type="color" id="favcolor" name="favcolor" style="margin-left: 4rem" />
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
      activeObject: null
    }
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
    // this.timer = setInterval(this.refreshCanvas, 10000)
  },
  methods: {
    async getBoardContent () {
      try {
        this.boardId = '161343a0-7dfa-45ea-b0b5-eca914a28c03'
        const res = await axios.get('http://localhost:8081/boards/' + this.boardId + '/content')
        // console.log(res.data)
        this.drawStickyNote(res.data.figureDtos)
      } catch (err) {
        console.log(err)
      }
    },
    async createStickyNote () {
      try {
        const res = await axios.post('http://localhost:8081/board/' + this.boardId + '/createStickyNote',
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
    async editStickyNote (figure) {
      try {
        const res = await axios.post('http://localhost:8081/board/' + this.boardId + '/editStickyNote',
          {
            figureId: figure.get('id'),
            content: figure.get('content'), // todo : combine position
            style: {
              fontSize: figure.item(1).get('fontSize'),
              shape: 2,
              width: parseFloat(figure.width) * parseFloat(figure.get('scaleX')),
              height: parseFloat(figure.height) * parseFloat(figure.get('scaleY')),
              color: figure.item(0).get('fill')
            }
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
        const res = await axios.post('http://localhost:8081/board/' + this.boardId + '/moveStickyNote',
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
      this.refreshCanvas()
    },
    async deleteStickyNote (figure) {
      try {
        const res = await axios.post('http://localhost:8081/board/' + this.boardId + '/deleteStickyNote',
          {
            figureId: figure.get('id')
          }
        )
        console.log(res.data.message)
      } catch (err) {
        console.log(err)
      }
      this.refreshCanvas()
    },
    async changeFigureOrder () {
      try {
        var objects = this.canvas.getObjects()
        var flist = []
        for (var i = 0; i < objects.length; i++) {
          flist.push(objects[i].get('id'))
        }
        console.log(flist)
        const res = await axios.post('http://localhost:8081/boards/' + this.boardId + '/changeFigureOrder',
          {
            figureOrderList: flist
          }
        )
        console.log(res.data.message)
      } catch (err) {
        console.log(err)
      }
      this.refreshCanvas()
    },
    initCanvas () {
      var width = Math.max(document.documentElement.clientWidth, window.innerWidth || 0)
      var height = Math.max(document.documentElement.clientHeight, window.innerHeight || 0)
      this.canvas = new fabric.Canvas('canvas', {
        width: width,
        height: height,
        fireRightClick: true, // <-- enable firing of right click events
        stopContextMenu: true // <--  prevent context menu from showing
      })
      // this.canvas.setBackgroundColor('gray')
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
    },
    listenEventsOnCanvas () {
      var _this = this
      var canvas = this.canvas
      canvas.on(
        {
          'mouse:dblclick': function (e) {
            console.log('object:dblclick')
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
                _this.editStickyNote(group)
              })
            }
          },
          'mouse:down': function (e) {
            _this.hideContextMenu()
            if (e.target != null) {
              if (e.button === 3) { // right click
                _this.showContextMenu(e)
                _this.addListenerOfChangeTextFigureColor(e)
                _this.addListenerOfDeleteTextFigure(e)
                _this.addListenerOfBringToFront(e)
                _this.addListenerOfBringForward(e)
                _this.addListenerOfSendBackward(e)
                _this.addListenerOfSendToBack(e)
              }
            }
          },
          'object:scaled': function (e) {
            console.log('object:scaled')
            _this.editStickyNote(e.target)
            _this.moveStickyNote(e.target)
          },
          'object:moved': function (e) {
            _this.moveStickyNote(e.target)
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
    },
    hideContextMenu () {
      this.contextMenu.style.display = 'none'
    },
    addListenerOfChangeTextFigureColor (e) {
      var _this = this
      console.log('current Color etarget:', e.target.get('id'))
      this.activeObject = this.canvas.getActiveObject()
      _this.favcolor.value = e.target.item(0).get('fill')
      console.log('be color:', _this.favcolor.value)
      var newHandler = function () {
        e.target.item(0).set('fill', _this.favcolor.value) // rect fill
        _this.editStickyNote(e.target)
        _this.hideContextMenu()
        _this.favcolor.removeEventListener('change', newHandler)
      }
      _this.favcolor.addEventListener('change', newHandler)
    },
    addListenerOfDeleteTextFigure (e) {
      var _this = this
      var newHandler = function () {
        console.log('del in id:', e.target.get('id'))
        _this.deleteStickyNote(e.target)
        _this.hideContextMenu()
        _this.delButton.removeEventListener('mouseup', newHandler)
      }
      this.delButton.removeEventListener('mouseup', newHandler)
      console.log('current Delete etarget:', e.target.get('id'))
      _this.delButton.addEventListener('mouseup', newHandler)
    },
    addListenerOfBringToFront (e) {
      var _this = this
      console.log('addListenerOfBringToFront')
      var newHandler = function () {
        e.target.bringToFront()
        _this.changeFigureOrder()
        _this.hideContextMenu()
        _this.bringToFrontButton.removeEventListener('mouseup', newHandler)
      }
      _this.bringToFrontButton.addEventListener('mouseup', newHandler)
    },
    addListenerOfBringForward (e) {
      var _this = this
      console.log('addListenerOfBringForward')
      var newHandler = function () {
        e.target.bringForward()
        _this.changeFigureOrder()
        _this.hideContextMenu()
        _this.bringForwardButton.removeEventListener('mouseup', newHandler)
      }
      _this.bringForwardButton.addEventListener('mouseup', newHandler)
    },
    addListenerOfSendBackward (e) {
      var _this = this
      console.log('addListenerOfSendBackward')
      var newHandler = function () {
        e.target.sendBackwards()
        _this.changeFigureOrder()
        _this.hideContextMenu()
        _this.sendBackwardButton.removeEventListener('mouseup', newHandler)
      }
      _this.sendBackwardButton.addEventListener('mouseup', newHandler)
    },
    addListenerOfSendToBack (e) {
      var _this = this
      console.log('addListenerOfSendToBack')
      var newHandler = function () {
        e.target.sendToBack()
        _this.changeFigureOrder()
        _this.hideContextMenu()
        _this.sendToBackButton.removeEventListener('mouseup', newHandler)
      }
      _this.sendToBackButton.addEventListener('mouseup', newHandler)
    },
    getZindex (e) {
      console.log(this.canvas.getObjects().indexOf(e))
    }

  }

}
</script>
