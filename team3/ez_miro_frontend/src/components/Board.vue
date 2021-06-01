<template>
  <div class="board" oncontextmenu="return false">
    <button type="button" @click="setWidgetTypeOfCreation(CREATE_WIDGET_TYPE.LINE)">Line</button>
    <button type="button" @click="setWidgetTypeOfCreation(CREATE_WIDGET_TYPE.STICKY_NOTE)">Sticky Note</button>
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
import { GetBoardContent, EnterBoard, MoveCursor } from '@/apis/Boards'
import {
  CreateStickyNote,
  DeleteStickyNoteBy,
  MoveStickyNoteBy,
  ResizeStickyNoteBy,
  EditTextOfStickyNoteBy,
  ChangeColorOfStickyNoteBy,
  ChangeZOrderOfStickyNoteBy,
  EditFontSizeOfStickyNoteBy,
  CreateLine
} from '@/apis/Widget'
import '@/models/StickyNote'
import '@/models/Line'
import { fabric } from 'fabric'
import { webSocketHost } from '@/config/config'

export default {
  data () {
    return {
      CREATE_WIDGET_TYPE: {
        NONE: null,
        STICKY_NOTE: 0,
        LINE: 1
      },
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
      isSamplingWidgetDelayFinish: true,
      user: null,
      collaborator: [],
      widgetTypeOfCreation: 0
    }
  },
  async created () {
    this.widgetTypeOfCreation = this.CREATE_WIDGET_TYPE.STICKY_NOTE
    this.boardId = this.$route.params.boardId
    this.boardContent = await GetBoardContent(this.boardId)
    this.initCanvas()
    this.loadAllStickyNote(this.boardContent.widgetDtos)
    this.user = this.createStubUser()
    this.initWebSocketAndBingEventListener()
    // ==================================
    this.createLine()
    // ==================================
  },
  methods: {
    createLine () {
      this.canvas.add(new fabric.OurLine({
        id: 'lineId',
        coors: [100, 100, 250, 250]
      }, {
        fill: 'black',
        stroke: 'black',
        strokeWidth: 5,
        selectable: true,
        evented: true
      }))
      this.canvas.renderAll()
    },
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
      this.webSocket.onopen = async function (e) {
        console.log('Successfully connected to the echo websocket server...')
        console.log(me.boardId, me.user.name)
        await EnterBoard(me.boardId, me.user.name)
      }
      this.webSocket.onmessage = async function (e) {
        const message = await JSON.parse(e.data)
        if (message.domainEvent === 'widgetDeletionNotifiedToAllUser') {
          me.whenWidgetDeleted(message.widgets)
        } else if (message.domainEvent === 'notifyTextOfWidgetModifiedToAllUser') {
          me.whenTextOfWidgetEdited(message.widgets)
        } else if (message.domainEvent === 'notifyWidgetResizedToAllUser') {
          me.whenWidgetResized(message.widgets)
        } else if (message.domainEvent === 'notifyColorOfWidgetModifiedToAllUser') {
          me.whenColorOfWidgetChanged(message.widgets)
        } else if (message.domainEvent === 'notifyWidgetZOrderRearrangedToAllUser') {
          me.whenZOrderOfWidgetChanged(message.widgets)
        } else if (message.domainEvent === 'notifyLineCreatedToAllUser') {
          me.whenLineCreated(message.widgets)
        } else {
          me.handleCursorMessage(message.cursors)
          me.handleWidgetMessage(message.widgets)
        }
      }
    },
    whenWidgetDeleted (widgets) {
      for (let index = 0; index < widgets.length; index++) {
        if (this.boardContent.widgetDtos.some(widgetDto => widgetDto.widgetId === widgets[index].widgetId)) {
          this.deleteWidgetInCanvas(widgets[index])
        }
      }
    },
    deleteWidgetInCanvas (widgetDto) {
      const canvas = this.canvas
      canvas.getObjects().forEach(function (o) {
        if (o.id === widgetDto.widgetId) {
          canvas.remove(o)
        }
      })
      canvas.renderAll()
    },
    whenTextOfWidgetEdited (widgets) {
      for (let index = 0; index < widgets.length; index++) {
        if (this.boardContent.widgetDtos.some(widgetDto => widgetDto.widgetId === widgets[index].widgetId)) {
          this.editTextOfWidget(widgets[index])
        }
      }
    },
    editTextOfWidget (widgetDto) {
      const canvas = this.canvas
      canvas.getObjects().forEach(function (o) {
        if (o.id === widgetDto.widgetId) {
          o.textObject.set('text', widgetDto.text)
        }
      })
      canvas.renderAll()
    },
    whenWidgetResized (widgets) {
      for (let index = 0; index < widgets.length; index++) {
        if (this.boardContent.widgetDtos.some(widgetDto => widgetDto.widgetId === widgets[index].widgetId)) {
          this.resizeWidget(widgets[index])
        }
      }
    },
    resizeWidget (widgetDto) {
      const canvas = this.canvas
      const me = this
      canvas.getObjects().forEach(function (o) {
        if (o.id === widgetDto.widgetId) {
          canvas.remove(o)
          me.loadStickyNoteIntoCanvas(widgetDto)
        }
      })
      canvas.renderAll()
    },
    whenColorOfWidgetChanged (widgets) {
      for (let index = 0; index < widgets.length; index++) {
        if (this.boardContent.widgetDtos.some(widgetDto => widgetDto.widgetId === widgets[index].widgetId)) {
          this.colorOfWidgetChanged(widgets[index])
        }
      }
    },
    whenZOrderOfWidgetChanged (widgets) {
      for (let index = 0; index < widgets.length; index++) {
        if (this.boardContent.widgetDtos.some(widgetDto => widgetDto.widgetId === widgets[index].widgetId)) {
          this.zOrderOfWidgetChanged(widgets[index])
        }
      }
    },
    whenLineCreated (widgetDto) {
      this.boardContent.widgetDtos.push(widgetDto)
      this.addWidgetToCanvas(widgetDto)
    },
    colorOfWidgetChanged (widgetDto) {
      const canvas = this.canvas
      canvas.getObjects().forEach(function (o) {
        if (o.id === widgetDto.widgetId) {
          o.rectObject.set('fill', widgetDto.color)
        }
      })
      canvas.renderAll()
    },
    zOrderOfWidgetChanged (widgetDto) {
      const canvas = this.canvas
      canvas.getObjects().forEach(function (o) {
        if (o.id === widgetDto.widgetId) {
          canvas.moveTo(o, widgetDto.zorder)
        }
      })
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
            this.updateWidgetInCanvas(widgets[index])
          } else {
            this.boardContent.widgetDtos.push(widgets[index])
            this.addWidgetToCanvas(widgets[index])
          }
        }
      }
    },
    updateWidgetInCanvas (widgetDto) {
      const canvas = this.canvas
      const activeWidget = this.canvas.getActiveObject()
      this.canvas.getObjects().forEach(function (o) {
        if (o.id === widgetDto.widgetId && (!activeWidget || activeWidget.id !== widgetDto.widgetId)) {
          o.animate('left', widgetDto.topLeftX, {
            duration: 200,
            onChange: canvas.renderAll.bind(canvas),
            easing: fabric.util.ease.easeInOutSine
          })
          o.animate('top', widgetDto.topLeftY, {
            duration: 200,
            onChange: canvas.renderAll.bind(canvas),
            easing: fabric.util.ease.easeInOutSine
          })
        }
      })
      this.canvas.renderAll()
    },
    addWidgetToCanvas (widgetDto) {
      this.loadStickyNoteIntoCanvas(widgetDto)
      // TODO: 後端傳過的 DTO 要給 type 的屬性
      // if (widgetDto.type === 'StickyNote') {

      // } else if (widgetDto.type === 'Line') {
      //   this.loadLineIntoCanvas(widgetDto)
      // }
    },
    bindCanvasEventListener () {
      const me = this
      this.canvas.on('mouse:move', function (e) {
        if (me.isSamplingCursorDelayFinish) {
          me.isSamplingCursorDelayFinish = false
          setTimeout(function () {
            me.isSamplingCursorDelayFinish = true
            MoveCursor(me.boardId, me.composeCursorInfo(e.absolutePointer.x, e.absolutePointer.y))
          }, 100)
        }
      })
      this.canvas.on('mouse:dblclick', function (e) {
        const info = {}
        const width = 100
        if (e.target !== null) {
          me.ungroup(e.target)
          const textObject = e.target.textObject
          me.canvas.setActiveObject(textObject)
          textObject.enterEditing()
          textObject.selectAll()
        } else {
          if (me.widgetTypeOfCreation === me.CREATE_WIDGET_TYPE.STICKY_NOTE) {
            info.topLeftX = e.absolutePointer.x - width / 2
            info.topLeftY = e.absolutePointer.y - width / 2
            info.bottomRightX = info.topLeftX + width
            info.bottomRightY = info.topLeftY + width
            CreateStickyNote(me.boardId, info)
          } else if (me.widgetTypeOfCreation === me.CREATE_WIDGET_TYPE.LINE) {
            info.topLeftX = 100
            info.topLeftY = 100
            info.bottomRightX = 250
            info.bottomRightY = 100
            CreateLine(me.boardId, info)
            me.setWidgetTypeOfCreation(me.CREATE_WIDGET_TYPE.STICKY_NOTE)
          }
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
      this.canvas.on('object:moving', function (o) {
        const target = o.target
        const stickyNoteId = target.id
        const point = target.lineCoords
        const topLeftX = point.tl.x
        const topLeftY = point.tl.y
        const bottomRightX = point.br.x
        const bottomRightY = point.br.y

        if (me.isSamplingWidgetDelayFinish) {
          me.isSamplingWidgetDelayFinish = false
          setTimeout(function () {
            me.isSamplingWidgetDelayFinish = true
            MoveStickyNoteBy(me.boardId, {
              [stickyNoteId]: {
                topLeftX: topLeftX,
                topLeftY: topLeftY,
                bottomRightX: bottomRightX,
                bottomRightY: bottomRightY
              }
            })
          }, 200)
        }
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
        this.isDisplayRightClickMenu = false
        this.selectedStickyNote = null
      }
    },
    async loadAllStickyNote (widgets) {
      await widgets.forEach(widget => { this.canvas.add(this.buildFabricObjectOfStickyNote(widget)) })
      this.canvas.renderAll()
    },
    async loadStickyNoteIntoCanvas (widgetDto) {
      await this.canvas.add(this.buildFabricObjectOfStickyNote(widgetDto))
      this.canvas.renderAll()
    },
    async loadLineIntoCanvas (widgetDto) {
      await this.canvas.add(this.buildFabricObjectOfLine(widgetDto))
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
    buildFabricObjectOfLine (widget) {
      return new fabric.OurLine({
        id: widget.widgetId,
        coors: [widget.topLeftX, widget.topLeftY, widget.bottomRightX, widget.bottomRightY]
      })
    },
    getZOrderOf (widget) {
      return this.canvas.getObjects().indexOf(widget)
    },
    composeCursorInfo (x, y) {
      return { x: Math.floor(x), y: Math.floor(y), userId: this.user.name }
    },
    composeWidgetInfo (widget) {
      return JSON.stringify({ widgets: [widget.widgetDto] })
    },
    setWidgetTypeOfCreation (widgetType) {
      this.widgetTypeOfCreation = widgetType
    }
  }
}
</script>
