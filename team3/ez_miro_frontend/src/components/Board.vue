<template>
    <div>
        <button @click="drawARect">畫圖</button>
        <canvas ref='board'></canvas>
    </div>
</template>

<script>
export default {
  data () {
    return {
      canvasContext: null
    }
  },
  mounted () {
    this.initCanvas()
    this.drawARect()
  },
  methods: {
    initCanvas () {
      const canvas = this.$refs.board
      canvas.width = window.innerWidth
      canvas.height = window.innerHeight - 60
      const ctx = canvas.getContext('2d')
      ctx.lineCap = 'round'
      ctx.lineJoin = 'round'
      this.canvasContext = ctx
    },
    drawARect () {
      const ctx = this.canvasContext
      ctx.fillStyle = '#000000'
      ctx.fillRect(10, 10, 100, 100)
      ctx.fillStyle = 'red'
      ctx.fillRect(20, 20, 30, 30)
    }
  }
}
</script>
