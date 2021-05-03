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
                    <input type="color" id="favcolor" name="favcolor" style="margin-left: 4rem" >
                  </li>
                </label>
                <li id="delButton">Delete</li>
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
      favcolor: null
    }
  },
  async mounted () {
    this.boardContent = this.getBoardContent()
    this.initCanvas()
    this.canvas.renderAll()
    this.listenEventsOnCanvas()
    this.contextMenu = document.getElementById('contextMenu')
    this.delButton = document.getElementById('delButton')
    this.favcolor = document.getElementById('favcolor')
    // this.timer = setInterval(this.refreshCanvas, 10000)
  },
  methods: {
    async getBoardContent () {
      try {
        this.boardId = '8a479b03-22e8-4b9c-b489-74d4caf6d6ab'
        const res = await axios.get('http://localhost:8081/boards/' + this.boardId + '/content')
        console.log(res.data)
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
              fontSize: 12,
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
            content: figure.get('content'),
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
    initCanvas () {
      this.canvas = new fabric.Canvas('canvas', {
        width: window.innerWidth,
        height: window.innerHeight,
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
              console.log('rect:', rect)
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
                console.log('current:', canvas.getObjects())
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
                // exteact to multi listener
                _this.favcolor.value = e.target.item(0).get('fill')
                _this.favcolor.addEventListener('change', function handler () {
                  e.target.item(0).set('fill', _this.favcolor.value) // rect fill
                  _this.editStickyNote(e.target)
                  _this.hideContextMenu()
                  this.removeEventListener('change', handler)
                })
                _this.delButton.addEventListener('click', function handler () {
                  _this.deleteStickyNote(e.target)
                  _this.hideContextMenu()
                  this.removeEventListener('click', handler)
                }, false)
              }
            }
          },
          'object:scaled': function (e) {
            console.log('object:scaled')
            _this.editStickyNote(e.target)
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
    }
  }

}
</script>
