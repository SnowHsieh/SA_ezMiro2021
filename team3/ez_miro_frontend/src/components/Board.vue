<template>
  <div class="board" oncontextmenu="return false">
    <canvas id="canvas" ref='board'></canvas>
    <ul class="right-click-menu list-group" :style="rightClickMenuStyle" :class="{'right-click-menu-display': isDisplayRightClickMenu}">
      <li @click="deleteWidget()" class="list-group-item">Delete</li>
      <li class="list-group-item">123</li>
      <li class="list-group-item">123</li>
    </ul>
  </div>
</template>

<script>
import { GetBoardContent } from '@/apis/Boards'
import { CreateStickyNote, ReadStickyNoteBy, DeleteStickyNoteBy } from '@/apis/Widget'
import '@/models/StickyNote'
import { fabric } from 'fabric'

export default {
  data () {
    return {
      canvasContext: null,
      boardContent: null,
      canvas: null,
      boardId: this.$route.params.boardId,
      isDisplayRightClickMenu: false,
      rightClickMenuStyle: {
        top: '-1px',
        left: '-1px'
      },
      selectedStickyNote: null
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

    this.canvas.on('mouse:up', async function (e) {
      // 臭到不行
      console.log(e)
      if (e.button === 3) {
        me.isDisplayRightClickMenu = true
        const point = e.absolutePointer
        me.rightClickMenuStyle.top = point.y + 'px'
        me.rightClickMenuStyle.left = point.x + 'px'
        me.setTargetId(e.target)
      } else {
        me.isDisplayRightClickMenu = false
        me.setTargetId(null)
      }
    })
  },
  methods: {
    initCanvas () {
      this.canvas = new fabric.Canvas('canvas', {
        fireRightClick: true,
        width: window.innerWidth,
        height: window.innerHeight
      })
      this.canvas.add(new fabric.IText('QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ', {
        left: 10,
        top: 10,
        fill: '#00ff00'
      }))
      this.canvas.renderAll()
    },
    async deleteWidget () {
      const res = await DeleteStickyNoteBy(this.selectedStickyNote.id, this.boardId)
      if (res !== null) {
        this.canvas.remove(this.selectedStickyNote)
        this.canvas.renderAll()
        this.isDisplayRightClickMenu = false
        this.selectedStickyNote = null
      }
    },
    async loadAllStickyNote (widgets) {
      await widgets.forEach(widget => {
        this.canvas.add(
          new fabric.StickyNoteNew({
            id: widget.widgetId,
            left: widget.topLeftX,
            top: widget.topLeftY,
            height: widget.height,
            width: widget.width,
            fill: widget.color,
            text: '123123',
            textColor: widget.textColor
          })
        )
      })
      this.canvas.renderAll()
    },
    async loadStickyNoteBy (id) {
      const stickyNote = await ReadStickyNoteBy(id, this.boardId)
      await this.canvas.add(
        new fabric.StickyNoteNew({
          id: id,
          left: stickyNote.widgetDto.topLeftX,
          top: stickyNote.widgetDto.topLeftY,
          height: stickyNote.widgetDto.height,
          width: stickyNote.widgetDto.width,
          fill: stickyNote.widgetDto.color,
          text: '123123',
          textColor: stickyNote.widgetDto.textColor
        })
      )
      this.canvas.renderAll()
    },
    setTargetId (target) {
      if (target !== null) {
        this.selectedStickyNote = target
      } else {
        this.selectedStickyNote = null
      }
    }
  }
}
</script>
