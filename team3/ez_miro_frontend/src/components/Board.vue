<template>
    <div>
        <canvas id="canvas" ref='board'></canvas>
    </div>
</template>

<script>
import { GetBoardContent } from '@/apis/Boards'
import { CreateStickyNote, ReadStickyNoteBy } from '@/apis/Widget'
import '@/models/StickyNote'
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
    this.boardId = this.$route.params.boardId
    this.boardContent = await GetBoardContent(this.boardId)
    this.initCanvas()
    this.loadAllStickyNote(this.boardContent.widgetDtos)
    const me = this
    this.canvas.on('mouse:dblclick', async function (e) {
      const info = {}
      const width = 100
      info.topLeftX = e.absolutePointer.x - width / 2
      info.topLeftY = e.absolutePointer.y - width / 2
      info.bottomRightX = info.topLeftX + width
      info.bottomRightY = info.topLeftY + width
      const stickyNoteId = await CreateStickyNote(me.boardId, info)

      await me.loadStickyNoteBy(stickyNoteId)
    })
  },
  methods: {
    initCanvas () {
      this.canvas = new fabric.Canvas('canvas', {
        width: window.innerWidth,
        height: window.innerHeight
      })
    },
    async loadAllStickyNote (widgets) {
      await widgets.forEach(widget => {
        this.canvas.add(
          new fabric.StickyNote({
            id: widget.widgetId,
            left: widget.topLeftX,
            top: widget.topLeftY,
            height: widget.height,
            width: widget.width,
            fill: widget.color
          })
        )
      })
      this.canvas.renderAll()
    },
    async loadStickyNoteBy (id) {
      const stickyNote = await ReadStickyNoteBy(id, this.boardId)
      await this.canvas.add(
        new fabric.StickyNote({
          id: id,
          left: stickyNote.widgetDto.topLeftX,
          top: stickyNote.widgetDto.topLeftY,
          height: stickyNote.widgetDto.height,
          width: stickyNote.widgetDto.width,
          fill: stickyNote.widgetDto.color
        })
      )
      this.canvas.renderAll()
    }
  }
}
</script>
