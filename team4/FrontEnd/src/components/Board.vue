<template>
        <!-- <button @click="drawARect">畫圖</button> -->
        <div><button id="createStickyNoteButton" @click="createStickyNote()">Add New StickyNote</button>
          <canvas id="canvas" ref='board' >
            <div class="container" oncontextmenu="return showContextMenu(event);">
              <div id="contextMenu" class="context-menu">
                <ul>
                  <li>Delete</li>
                </ul>
              </div>
            </div>

          </canvas>
        </div>

</template>

<script>
// import { markRaw } from '@vue/reactivity'
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
      contextMenu: null
    }
  },
  async mounted () {
    this.boardContent = this.getBoardContent()
    this.initCanvas()
    this.canvas.renderAll()
    this.listenEventsOnCanvas()
    // this.contextMenu.$mount(contextMenu)
    this.contextMenu = document.getElementById('contextMenu')
    // this.timer = setInterval(this.refreshCanvas, 10000)
  },
  methods: {
    async getBoardContent () {
      try {
        this.boardId = 'e0f185ac-aaff-417c-b5cf-ff45db28cc54'
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
              color: figure.get('color')
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
    // async deleteStickyNote (figure) {
    //   try {
    //     const res = await axios.post('http://localhost:8081/board/' + this.boardId + '/deleteStickyNote',
    //         {
    //           figureId: figure.get('id')
    //         }
    //     )
    //     console.log(res.data.message)
    //   } catch (err) {
    //     console.log(err)
    //   }
    //   this.refreshCanvas()
    // },
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
          color: figure.style.color,
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
      // this.canvas.setBackgroundColor('gray')
      this.getBoardContent()
    },
    listenEventsOnCanvas () {
      var _this = this
      var canvas = this.canvas
      canvas.on(
        {
          'mouse:dblclick': function (e) {
            console.log('object:dblclick')
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
                color: rect.fill,
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
          },
          'mouse:down': function (e) {
            console.log('object:down')
            e.target.on('mousedown', function (event) {
              if (event.button === 3) {
                console.log('right click')
                _this.showContextMenu(e)
              }
            })
          },
          // 'mouse:click': function (e) {
          //   console.log('object:click')
          //   _this.hideContextMenu()
          // },
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
      console.log('ungroup flow')
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
      console.log('showContextMenu')
      this.contextMenu.style.display = 'block'
      this.contextMenu.style.left = event.clientX + 'px'
      this.contextMenu.style.top = event.clientY + 'px'
      return false
    }
    // hideContextMenu () {
    //   this.contextMenu.style.display = 'none';
    // },
    // listenKeys (event) {
    //   var keyCode = event.which || event.keyCode;
    //   if(keyCode == 27){
    //     this.hideContextMenu();
    //   }
    // }
  }

}
</script>
