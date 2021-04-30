<template>
    <div>
        <!-- <button @click="drawARect">畫圖</button> -->
        <canvas id="canvas" ref='board'></canvas>
    </div>
</template>

<script>
import { GetBoardContent } from '@/apis/Boards'
// import { markRaw } from '@vue/reactivity'
import { fabric } from 'fabric'

export default {
  data () {
    return {
      canvasContext: null,
      boardContent: null,
      canvas: null
    }
  },
  async created () {
    this.boardContent = await GetBoardContent('firstId')
    this.initCanvas()
    this.createStickyNote()
  },
  methods: {
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
