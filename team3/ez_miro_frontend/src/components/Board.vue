<template>
    <div>
        <button @click='createStickyNote()'>Create Sticky Note</button>
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
      canvas: null,
      boardId: this.$route.params.boardId
    }
  },
  async created () {
    console.log(this.boardId)
    this.boardContent = await GetBoardContent(this.boardId)
    this.initCanvas()
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
