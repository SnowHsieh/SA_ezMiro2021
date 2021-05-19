<template>
  <div class="board" oncontextmenu="return false">
    <canvas id="canvas" ref='board'></canvas>
    <ul class="right-click-menu list-group" :style="rightClickMenuStyle" :class="{'right-click-menu-display': isDisplayRightClickMenu}">
      <li @click="deleteWidget()" class="list-group-item">Delete</li>
      <li class="list-group-item">
        <input type="color" id="favcolor" name="favcolor" v-model="selectedStickyNoteColor" @change="changeColorOfStickyNoteWith(selectedStickyNoteColor)">Custom Color</li>
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
    <div class="cursors" v-for="user in this.collaborator" v-bind:key="user.userId" :style="{'top': user.y + 'px', 'left': user.x + 'px'}">
      {{user.userId}}
    </div>
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
  ChangeZOrderOfStickyNoteBy,
  EditFontSizeOfStickyNoteBy
} from '@/apis/Widget'
import '@/models/StickyNote'
import { fabric } from 'fabric'
import { webSocketHost } from '@/config/config'

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
      webSocket: null,
      isSamplingCursorDelayFinish: true,
      user: null,
      collaborator: []
    }
  },
  async created () {
    this.boardId = this.$route.params.boardId
    this.boardContent = await GetBoardContent(this.boardId)
    this.initCanvas()
    this.loadAllStickyNote(this.boardContent.widgetDtos)
    this.user = this.createStubUser()
    this.initWebSocketAndBingEventListener()
  },
  methods: {
    initCanvas () {
      this.canvas = new fabric.Canvas('canvas', {
        fireRightClick: true,
        width: window.innerWidth,
        height: window.innerHeight
      })
      this.bindCanvasEventListener()
    },
    createStubUser () {
      return {
        name: `匿名北極熊${Math.floor((Math.random() * 100) + 1)}`,
        x: 0,
        y: 0
      }
    },
    initWebSocketAndBingEventListener () {
      const me = this
      this.webSocket = new WebSocket(`${webSocketHost}/WebSocketServer/${this.boardId}/${this.user.name}`)
      this.webSocket.onopen = function (e) {
        console.log('Successfully connected to the echo websocket server...')
      }
      this.webSocket.onmessage = async function (e) {
        const message = await JSON.parse(e.data)
        me.handleCursorMessage(message.cursors)
        me.handleWidgetMessage(message.widgets)
      }
    },
    handleCursorMessage (cursors) {
      if (cursors !== undefined) {
        for (let index = 0; index < cursors.length; index++) {
          if (cursors[index].userId === this.user.name) {
            cursors.splice(index, 1)
          }
        }
        this.collaborator = cursors
      }
    },
    handleWidgetMessage (widgets) {
      if (widgets !== undefined) {
        for (let index = 0; index < widgets.length; index++) {
          if (this.boardContent.widgetDtos.some(widgetDto => widgetDto.widgetId === widgets[index].widgetId)) {
            this.updateWidgetInCanvas()
          } else {
            this.addWidgetToCanvas()
          }
        }
      }
    },
    updateWidgetInCanvas () {
    },
    addWidgetToCanvas () {
    },
    bindCanvasEventListener () {
      const me = this
      this.canvas.on('mouse:move', function (e) {
        if (me.isSamplingCursorDelayFinish) {
          me.isSamplingCursorDelayFinish = false
          setTimeout(function () {
            me.isSamplingCursorDelayFinish = true
            me.webSocket.send(me.composeCursorInfo(e.absolutePointer.x, e.absolutePointer.y))
          }, 100)
        }
      })
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
          const stickyNote = await ReadStickyNoteBy(stickyNoteId, me.boardId)
          await me.loadStickyNoteIntoCanvas(stickyNote)
          me.webSocket.send(me.composeWidgetInfo(stickyNote))
        }
      })

      this.canvas.on('mouse:up', async function (e) {
        const target = e.target
        if (e.button === 1) {
          me.isDisplayRightClickMenu = false
          if (target) {
            me.setTarget(target)
            me.oldPoint = target.lineCoords
          }
        } else if (e.button === 3) { // 臭到不行
          me.isDisplayRightClickMenu = true
          const point = e.absolutePointer
          me.rightClickMenuStyle.top = point.y + 'px'
          me.rightClickMenuStyle.left = point.x + 'px'
          me.setTarget(target)
        } else {
          me.isDisplayRightClickMenu = false
          me.setTarget(null)
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
        await EditFontSizeOfStickyNoteBy(stickyNoteId, me.boardId, e.target.textObject.fontSize)
        await ResizeStickyNoteBy(stickyNoteId, me.boardId, {
          topLeftX: topLeftX,
          topLeftY: topLeftY,
          bottomRightX: bottomRightX,
          bottomRightY: bottomRightY
        })
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
      await widgets.forEach(widget => { this.canvas.add(this.buildFabricObjectOfStickyNote(widget)) })
      this.canvas.renderAll()
    },
    async loadStickyNoteIntoCanvas (stickyNote) {
      await this.canvas.add(this.buildFabricObjectOfStickyNote(stickyNote.widgetDto))
      this.canvas.renderAll()
    },
    setTarget (target) {
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
        canvas.add(new fabric.StickyNote({
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
    async bringToFront () {
      await this.canvas.bringToFront(this.selectedStickyNote)
      const zOrder = this.getZOrderOf(this.selectedStickyNote)
      await ChangeZOrderOfStickyNoteBy(this.selectedStickyNote.id, this.boardId, zOrder)
      this.isDisplayRightClickMenu = false
    },
    async sendToback () {
      await this.canvas.sendToBack(this.selectedStickyNote)
      const zOrder = this.getZOrderOf(this.selectedStickyNote)
      await ChangeZOrderOfStickyNoteBy(this.selectedStickyNote.id, this.boardId, zOrder)
      this.isDisplayRightClickMenu = false
    },
    buildFabricObjectOfStickyNote (widget) {
      return new fabric.StickyNote({
        id: widget.widgetId,
        left: widget.topLeftX,
        top: widget.topLeftY,
        height: widget.height,
        width: widget.width,
        fill: widget.color,
        text: widget.text,
        textColor: widget.textColor,
        fontSize: widget.fontSize
      })
    },
    getZOrderOf (widget) {
      return this.canvas.getObjects().indexOf(widget)
    },
    composeCursorInfo (x, y) {
      return JSON.stringify({ cursor: { x: Math.floor(x), y: Math.floor(y) } })
    },
    composeWidgetInfo (widget) {
      return JSON.stringify({ widget: widget.widgetDto })
    }
  }
}
</script>
