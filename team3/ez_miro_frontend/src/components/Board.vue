<template>
  <div class="board" oncontextmenu="return false">
    <canvas id="canvas" ref='board'></canvas>
    <ul class="right-click-menu list-group" :style="rightClickMenuStyle" :class="{'right-click-menu-display': isDisplayRightClickMenu}">
      <li @click="deleteWidget()" class="list-group-item">Delete</li>
      <li class="list-group-item">
        <input type="color" id="favcolor" name="favcolor" v-model="selectedStickyNoteColor" @change="changeColorOfStickyNote">Custom Color</li>
      <li class="list-group-item">
        <button type="button" class="btn btn-default btn-circle" style="background-color: #4CAF50;" @click="changeColorOfStickyNoteWith('#4CAF50')"></button>
        <button type="button" class="btn btn-default btn-circle" style="background-color: #41ABD8;" @click="changeColorOfStickyNoteWith('#41ABD8')"></button>
        <button type="button" class="btn btn-default btn-circle" style="background-color: #FFFAAD;" @click="changeColorOfStickyNoteWith('#FFFAAD')"></button>
        <button type="button" class="btn btn-default btn-circle" style="background-color: #FFB22E;" @click="changeColorOfStickyNoteWith('#FFB22E')"></button>
        <button type="button" class="btn btn-default btn-circle" style="background-color: #CB56F5;" @click="changeColorOfStickyNoteWith('#CB56F5')"></button>
      </li>
      <li class="list-group-item" @click="bringToFront">bring to front</li>
      <li class="list-group-item" @click="sendToback">send to back</li>
    </ul>
  </div>
</template>

<script>
import { GetBoardContent } from '@/apis/Boards'
import {
  CreateStickyNote,
  ReadStickyNoteBy,
  DeleteStickyNoteBy,
  MoveStickyNoteBy,
  ResizeStickyNoteBy,
  EditTextOfStickyNoteBy,
  ChangeColorOfStickyNoteBy,
  EditZIndexOfStickyNoteBy
} from '@/apis/Widget'
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
      selectedStickyNote: null,
      ungroupTarget: {},
      selectedStickyNoteColor: '#000000',
      indexMax: 0, // TODO: 邏輯待改善
      indexMin: 0 // TODO: 邏輯待改善
    }
  },
  async created () {
    this.boardId = this.$route.params.boardId
    this.boardContent = await GetBoardContent(this.boardId)
    this.initCanvas()
    this.boardContent.widgetDtos.sort((a, b) => a.zIndex - b.zIndex)
    this.loadAllStickyNote(this.boardContent.widgetDtos)
    const me = this
    this.canvas.on('mouse:dblclick', async function (e) {
      const info = {}
      const width = 100
      if (e.target !== null) {
        me.ungroup(e.target)
        const textObject = e.target.textObject
        me.canvas.setActiveObject(textObject)
        textObject.enterEditing()
        textObject.selectAll()
      } else {
        info.topLeftX = e.absolutePointer.x - width / 2
        info.topLeftY = e.absolutePointer.y - width / 2
        info.bottomRightX = info.topLeftX + width
        info.bottomRightY = info.topLeftY + width
        const stickyNoteId = await CreateStickyNote(me.boardId, info)
        await me.loadStickyNoteBy(stickyNoteId)
      }
    })

    this.canvas.on('mouse:up', async function (e) {
      // 臭到不行
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

    this.canvas.on('object:moved', async function (e) {
      const target = e.target
      const stickyNoteId = target.id
      const point = target.lineCoords
      const topLeftX = point.tl.x
      const topLeftY = point.tl.y
      const bottomRightX = point.br.x
      const bottomRightY = point.br.y
      await MoveStickyNoteBy(me.boardId, {
        [stickyNoteId]: {
          topLeftX: topLeftX,
          topLeftY: topLeftY,
          bottomRightX: bottomRightX,
          bottomRightY: bottomRightY
        }
      })
    })

    this.canvas.on('object:scaled', async function (e) {
      const target = e.target
      const stickyNoteId = target.id
      const point = target.lineCoords
      const topLeftX = point.tl.x
      const topLeftY = point.tl.y
      const bottomRightX = point.br.x
      const bottomRightY = point.br.y
      await ResizeStickyNoteBy(stickyNoteId, me.boardId, {
        topLeftX: topLeftX,
        topLeftY: topLeftY,
        bottomRightX: bottomRightX,
        bottomRightY: bottomRightY
      })
    })
  },
  methods: {
    initCanvas () {
      this.canvas = new fabric.Canvas('canvas', {
        fireRightClick: true,
        width: window.innerWidth,
        height: window.innerHeight
      })
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
            text: widget.text,
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
          text: stickyNote.widgetDto.text,
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
    },
    ungroup (group) {
      this.ungroupTarget = group
      const items = group._objects
      group._restoreObjectsState()
      this.canvas.remove(group)
      this.canvas.renderAll()
      for (var i = 0; i < items.length; i++) {
        this.canvas.add(items[i])
      }
      this.canvas.renderAll()
      this.listenEndTextEditing(group.textObject)
    },
    listenEndTextEditing (textObject) {
      const canvas = this.canvas
      const group = this.ungroupTarget
      const me = this
      textObject.on('editing:exited', async function (e) {
        const objects = group._objects
        objects.forEach(item => {
          canvas.remove(item)
        })
        canvas.add(new fabric.StickyNoteNew({
          id: group.id,
          left: objects[0].left,
          top: objects[0].top,
          height: objects[0].height,
          width: objects[0].width,
          fill: objects[0].fill,
          text: objects[1].text,
          textColor: objects[1].fill
        }))
        canvas.renderAll()
        await EditTextOfStickyNoteBy(group.id, me.boardId, objects[1].text)
      })
    },
    async changeColorOfStickyNoteWith (color) {
      const res = await ChangeColorOfStickyNoteBy(this.selectedStickyNote.id, this.boardId, color)
      if (res !== null) {
        this.selectedStickyNote.rectObject.set('fill', color)
        this.canvas.renderAll()
      }
      this.isDisplayRightClickMenu = false
    },
    changeColorOfStickyNote () {
      this.changeColorOfStickyNoteWith(this.selectedStickyNoteColor)
    },
    async bringToFront () { // TODO: 邏輯待改善
      this.indexMax += 1
      await EditZIndexOfStickyNoteBy(this.selectedStickyNote.id, this.boardId, this.indexMax)
      await this.canvas.bringToFront(this.selectedStickyNote)
      this.isDisplayRightClickMenu = false
    },
    async sendToback () { // TODO: 邏輯待改善
      this.indexMin -= 1
      await EditZIndexOfStickyNoteBy(this.selectedStickyNote.id, this.boardId, this.indexMin)
      await this.canvas.sendToBack(this.selectedStickyNote)
      this.isDisplayRightClickMenu = false
    },
    sortStickyNotesByZIndex (widgets) {
      widgets.sort((a, b) => a.zIndex > b.zIndex)
    }
  }
}
</script>
