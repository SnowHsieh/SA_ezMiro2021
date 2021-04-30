<template>
        <!-- <button @click="drawARect">畫圖</button> -->
        <canvas id="canvas" ref='board'></canvas>
</template>

<script>
// import { markRaw } from '@vue/reactivity'
import { fabric } from 'fabric'
import axios from 'axios'

export default {
  data () {
    return {
      canvasContext: null,
      boardContent: null,
      canvas: null
    }
  },
  async created () {
    this.boardContent = this.getBoardContent()
    this.initCanvas()
    this.canvas.add(
      new fabric.Rect({
        left: 50,
        top: 50,
        height: 500,
        width: 500,
        fill: '#948787'
      })
    )
    this.canvas.renderAll()
  },
  methods: {
    async getBoardContent () {
      try {
        const res = await axios.get('http://localhost:8081/boards/35fa01ed-b68c-4c38-b620-9b6bd5e7d67e/content')
        console.log(res.data)
        // return res.data
        this.createStickyNote(res.data.figureDtos)
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
    createStickyNote (figureDtos) {
      var _this = this
      console.log('figureDtos')
      console.log(figureDtos)
      figureDtos.forEach(figure =>
        _this.canvas.add(
          new fabric.Rect({
            left: figure.position.x,
            top: figure.position.y,
            height: figure.style.figureSize,
            width: figure.style.figureSize,
            fillColor: figure.style.color
          })
        )
      )
      this.canvas.renderAll()
    }
  }
}
</script>
