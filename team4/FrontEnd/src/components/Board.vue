<template>
    <div>
        <!-- <button @click="drawARect">畫圖</button> -->
        <canvas id="canvas" ref='board'></canvas>
    </div>
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
    this.getBoardContent()
    this.initCanvas()
    // this.createStickyNote()
  },
  methods: {
    async getBoardContent () {
      try {
        const res = await axios.get('http://localhost:8081/boards/1e4c716f-b8ac-4d88-b7dc-4ea356528a95/content')
        console.log(res.data)
        return res.data
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
    createStickyNote () {
      this.boardContent.widgetDtos.forEach(widget =>
        this.canvas.add(
          new fabric.Rect({
            left: widget.topLeftX,
            top: widget.topLeftY,
            height: widget.height,
            width: widget.width,
            fill: widget.color
          })
        )
      )
      this.canvas.renderAll()
    }
  }
}
</script>
