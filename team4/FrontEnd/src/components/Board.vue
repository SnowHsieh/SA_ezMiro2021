<template>
        <!-- <button @click="drawARect">畫圖</button> -->
        <div><button id="createStickyNoteButton" @click="createStickyNote()">Add New StickyNote</button>

<!--          <div>-->
<!--            content: <input type="text" placeholder="" v-model="figurecontent"><br>-->

<!--          </div>-->

<!--        <div><button id="editStickyNoteButton" @click="editStickyNote('7d9813e1-6360-4b58-a333-b1935af350d2')">Edit StickyNote</button></div>-->
          <canvas id="canvas" ref='board' ></canvas></div>

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
      time: 0
    }
  },
  async mounted () {
    this.boardContent = this.getBoardContent()
    this.initCanvas()
    this.canvas.renderAll()
    this.listenEventsOnCanvas()
    // this.timer = setInterval(this.refreshCanvas, 10000)
  },
  methods: {
    // figurecontent: undefined,
    async getBoardContent () {
      try {
        this.boardId = '7b7d5bdb-d6d5-41f1-b32a-93f694c03811'
        const res = await axios.get('http://localhost:8081/boards/' + this.boardId + '/content')
        console.log(res.data)
        // return res.data
        this.drawStickyNote(res.data.figureDtos)
      } catch (err) {
        console.log(err)
      }
    },
    initCanvas () {
      this.canvas = new fabric.Canvas('canvas', {
        width: window.innerWidth,
        height: window.innerHeight
      })
    },

    drawStickyNote (figureDtos) {
      var _this = this
      figureDtos.forEach(figure => {
        console.log(figure)
        console.log(figure.style)
        var rect = new fabric.Rect({
          originX: 'center',
          originY: 'center',
          width: figure.style.width,
          height: figure.style.height,
          fill: figure.style.color
        })
        var text = new fabric.IText(figure.content, {
          originX: 'center',
          originY: 'center'
        }
        )

        var group = new fabric.Group([rect, text], {
          color: figure.style.color,
          content: figure.content,
          id: figure.figureId,
          left: figure.position.x,
          top: figure.position.y
        })

        // console.log(rect.extraData.figureId)
        _this.canvas.add(group)
        // _this.canvas.add(text)
      })
      this.canvas.renderAll()
    },
    refreshCanvas () {
      this.canvas.clear()
      this.getBoardContent()
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
              width: 100.0,
              height: 100.0,
              color: '#f9f900'
            }
          }
        )
        console.log(res.data.message)
        // return res.data
      } catch (err) {
        console.log(err)
      }
      // this.refreshCanvas()
    },
    async editStickyNote (figure) {
      try {
        console.log(figure)
        const res = await axios.post('http://localhost:8081/board/' + this.boardId + '/editStickyNote',
          {
            figureId: figure.get('id'),
            content: figure.get('content'),
            style: {
              fontSize: 50,
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
      // this.refreshCanvas()
    },
    listenEventsOnCanvas () {
      var _this = this
      var canvas = this.canvas
      canvas.on(
        {
          'mousedblclick': function (e) {
            console.log('object:mousedblclick')
            e.target.opacity = 0.5
          },
          'object:selected': function (e) {
            console.log('object:selected')
            e.target.opacity = 0.5
          },
          'object:scaled': function (e) {
            console.log('object:scaled')
            _this.editStickyNote(e.target)
          },
          'object:moved': function (e) {
            console.log('object:moved')
            _this.moveStickyNote(e.target)
          }


        })
    }
  }

}
</script>
