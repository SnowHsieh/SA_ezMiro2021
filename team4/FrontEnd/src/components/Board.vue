<template>
        <!-- <button @click="drawARect">畫圖</button> -->
        <div><button id="createStickyNoteButton" v-on:click="createStickyNote()">Add New StickyNote</button>
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
    // this.timer = setInterval(this.refreshCanvas, 10000)
  },

  methods: {
    async getBoardContent () {
      try {
        this.boardId = '81f749b2-9009-47f3-943c-0ade6e6a3a9b'
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
        var rect = new fabric.Rect({
          originX: 'center',
          originY: 'center',
          height: figure.style.figureSize,
          width: figure.style.figureSize,
          fill: figure.style.color
        })
        var text = new fabric.IText(figure.content, {
          originX: 'center',
          originY: 'center'
        }
        )
        rect.extraData = {
          figureId: figure.figureId
        }

        var group = new fabric.Group([rect, text], {
          left: figure.position.x,
          top: figure.position.y
        })

        console.log(rect.extraData.figureId)
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
              figureSize: 100.0,
              color: '#f28500'
            }
          }
        )
        console.log(res.data.message)
        // return res.data
      } catch (err) {
        console.log(err)
      }
    }
  }
}
</script>
