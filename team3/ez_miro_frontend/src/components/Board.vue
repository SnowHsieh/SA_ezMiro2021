<template>
  <div class="board" oncontextmenu="return false">
    <!-- <h5 class="ml-2" v-show="isDataLoaded">{{ user.name }}</h5> -->
    <button type="button" class="btn btn-success mr-3" @click="setWidgetTypeOfCreation(CREATE_WIDGET_TYPE.LINE)" v-show="isDataLoaded">Line</button>
    <button type="button" class="btn btn-warning" @click="setWidgetTypeOfCreation(CREATE_WIDGET_TYPE.STICKY_NOTE)" v-show="isDataLoaded">Sticky Note</button>
    <canvas id="canvas" ref='board' :class="canvasStyle"></canvas>
    <ul class="right-click-menu list-group" :style="rightClickMenuStyle" :class="{'right-click-menu-display': isDisplayRightClickMenu}">
      <li @click="deleteWidget()" class="list-group-item">Delete</li>
      <li class="list-group-item">
        <input type="color" id="favcolor" name="favcolor" v-model="selectedWidgetColor" @change="changeColorOfStickyNoteWith(selectedWidgetColor)">Custom Color</li>
      <li class="list-group-item">
        <button type="button" class="btn btn-default btn-circle" style="background-color: #4CAF50;" @click="changeColorOfStickyNoteWith('#4CAF50')"></button>
        <button type="button" class="btn btn-default btn-circle" style="background-color: #41ABD8;" @click="changeColorOfStickyNoteWith('#41ABD8')"></button>
        <button type="button" class="btn btn-default btn-circle" style="background-color: #FFFAAD;" @click="changeColorOfStickyNoteWith('#FFFAAD')"></button>
      </li>
      <li class="list-group-item">
        <button type="button" class="btn btn-default btn-circle" style="background-color: #FFB22E;" @click="changeColorOfStickyNoteWith('#FFB22E')"></button>
        <button type="button" class="btn btn-default btn-circle" style="background-color: #CB56F5;" @click="changeColorOfStickyNoteWith('#CB56F5')"></button>
        <button type="button" class="btn btn-default btn-circle" style="background-color: #F5E642;" @click="changeColorOfStickyNoteWith('#F5E642')"></button>
      </li>
      <li class="list-group-item" @click="bringToFront">bring to front</li>
      <li class="list-group-item" @click="sendToback">send to back</li>
    </ul>
    <div class="cursors" v-for="user in this.collaborator" v-bind:key="user.userId" :style="{'top': user.y + 'px', 'left': user.x + 'px'}">
      {{user.userId}}
    </div>
    <div class="d-flex justify-content-center">
    <button class="btn btn-outline-secondary" type="button" disabled>
      <span class="spinner-grow spinner-grow-sm" role="status" aria-hidden="true"></span>
      Loading...
    </button>
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
  CreateLine,
  MoveLineBy,
  LinkLine,
  DisconnectLine,
  DeleteLineBy
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
      selectedWidget: null,
      ungroupTarget: {},
      selectedWidgetColor: '#000000',
      webSocket: null,
      isSamplingCursorDelayFinish: true,
      isSamplingWidgetDelayFinish: true,
      isSamplingLineDelayFinish: true,
      user: { name: '' },
      collaborator: [],
      widgetTypeOfCreation: 0,
      isDataLoaded: false,
      canvasStyle: ''
    }
  },
  async created () {
    this.widgetTypeOfCreation = this.CREATE_WIDGET_TYPE.STICKY_NOTE
    this.boardId = this.$route.params.boardId
    this.boardContent = await GetBoardContent(this.boardId)
    this.boardContent.widgetDtos = []
    this.initCanvas()
    this.loadAllStickyNote(this.boardContent.stickyNoteDtos)
    this.loadAllLine(this.boardContent.lineDtos)
    this.user = this.createStubUser()
    this.initWebSocketAndBingEventListener()
    this.isDataLoaded = true
    this.canvasStyle = 'border border-secondary'
  },
  methods: {
    initCanvas () {
      this.canvas = new fabric.Canvas('canvas', {
        fireRightClick: true,
        // width: window.innerWidth,
        // height: window.innerHeight
        width: 10000,
        height: 10000
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
        if (message.domainEvent === 'widgetDeleted') {
          me.whenWidgetDeleted(message.widgets)
        } else if (message.domainEvent === 'textOfWidgetEdited') {
          me.whenTextOfWidgetEdited(message.widgets)
        } else if (message.domainEvent === 'widgetResized') {
          me.whenWidgetResized(message.widgets)
        } else if (message.domainEvent === 'colorOfWidgetChanged') {
          me.whenColorOfWidgetChanged(message.widgets)
        } else if (message.domainEvent === 'widgetZOrderChanged') {
          me.whenZOrderOfWidgetChanged(message.widgets)
        } else if (message.domainEvent === 'boardEntered') {
          me.handleCursorCreation(message.cursor)
        } else if (message.domainEvent === 'boardCursorMoved') {
          me.handleCursorMovement(message.cursor)
        } else if (message.domainEvent === 'boardLeft') {
          me.handleCursorDeletion(message.cursor)
        } else if (message.domainEvent === 'lineDisconnected') {
          me.handleLineDisconnection(message.line)
        } else {
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
          if (o.circleTail !== null) {
            canvas.remove(o.circleTail)
          }
          if (o.circleHead !== null) {
            canvas.remove(o.circleHead)
          }
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
          if (widgetDto.zorder === 0) {
            canvas.sendToBack(o)
          } else {
            canvas.bringToFront(o)
          }
          return false
        }
      })
    },
    handleCursorCreation (cursor) {
      console.log('someone entered board.')
      if (this.user.name !== cursor.userId) {
        this.collaborator.push(cursor)
        MoveCursor(this.boardId, this.composeCursorInfo(this.user.x, this.user.y))
      }
    },
    handleCursorMovement (cursor) {
      var user = this.collaborator.find(user => user.userId === cursor.userId)
      if (user !== undefined) {
        user.x = cursor.x
        user.y = cursor.y
      } else if (this.user.name !== cursor.userId) {
        this.collaborator.push(cursor)
      }
    },
    handleCursorDeletion (cursor) {
      console.log('someone left board.')
      console.log(cursor)
      for (let i = 0; i < this.collaborator.length; i++) {
        if (this.collaborator[i].userId === cursor.userId) {
          this.collaborator.splice(i, 1)
        }
      }
    },
    handleLineDisconnection (line) {
      const me = this
      this.canvas.getObjects().forEach(function (o) {
        if (o.id === line.lineId) {
          if (line.endPoint === 'head') {
            o.circleHead.connectedWidgetId = null
          } else {
            o.circleTail.connectedWidgetId = null
          }

          const newLineDto = {
            headWidgetId: o.circleHead.connectedWidgetId,
            tailWidgetId: o.circleTail.connectedWidgetId,
            topLeftX: o.x1,
            topLeftY: o.y1,
            bottomRightX: o.x2,
            bottomRightY: o.y2,
            type: 'line',
            widgetId: o.id
          }

          me.canvas.remove(o.circleHead)
          me.canvas.remove(o.circleTail)
          me.canvas.remove(o)
          o.circleHead.off('moving')
          o.circleTail.off('moving')
          o.off('moving')
          me.boardContent.widgetDtos.push(newLineDto)
          me.loadLineIntoCanvas(newLineDto)
          return false
        }
      })
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
          if (o.get('type') === 'stickyNote') {
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
          } else if (o.get('type') === 'line') {
            o.set({ x1: widgetDto.topLeftX, y1: widgetDto.topLeftY })
            o.circleHead.set({ top: o.y1 - o.circleHead.radius, left: o.x1 - o.circleHead.radius })
            o.set({ x2: widgetDto.bottomRightX, y2: widgetDto.bottomRightY })
            o.circleTail.set({ top: o.y2 - o.circleTail.radius, left: o.x2 - o.circleTail.radius })
          }
        }
      })
      this.canvas.renderAll()
    },
    addWidgetToCanvas (widgetDto) {
      if (widgetDto.type === 'stickyNote') {
        this.loadStickyNoteIntoCanvas(widgetDto)
      } else if (widgetDto.type === 'line') {
        this.loadLineIntoCanvas(widgetDto)
      }
    },
    bindCanvasEventListener () {
      const me = this
      this.canvas.on('mouse:move', function (e) {
        if (me.isSamplingCursorDelayFinish) {
          me.isSamplingCursorDelayFinish = false
          setTimeout(function () {
            me.isSamplingCursorDelayFinish = true
            me.user.x = e.absolutePointer.x
            me.user.y = e.absolutePointer.y
            MoveCursor(me.boardId, me.composeCursorInfo(me.user.x, me.user.y))
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
            info.topLeftX = e.absolutePointer.x - 50
            info.topLeftY = e.absolutePointer.y - 50
            info.bottomRightX = e.absolutePointer.x + 50
            info.bottomRightY = e.absolutePointer.y + 50
            CreateLine(me.boardId, info)
            me.setWidgetTypeOfCreation(me.CREATE_WIDGET_TYPE.STICKY_NOTE)
          }
        }
      })

      this.canvas.on('mouse:up', async function (e) {
        const target = e.target
        if (e.button === 1) { // 左鍵
          me.isDisplayRightClickMenu = false
          if (target) {
            me.setTarget(target)
            me.oldPoint = target.lineCoords
          }
        } else if (e.button === 3 && target !== null) { // 右鍵
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
        const widgetId = target.id
        const point = target.lineCoords
        const topLeftX = point.tl.x
        const topLeftY = point.tl.y
        const bottomRightX = point.br.x
        const bottomRightY = point.br.y
        if (target.get('type') === 'stickyNote') {
          await MoveStickyNoteBy(me.boardId, {
            [widgetId]: {
              topLeftX: topLeftX,
              topLeftY: topLeftY,
              bottomRightX: bottomRightX,
              bottomRightY: bottomRightY
            }
          })
        } else if (target.get('type') === 'line') {
          // do nothing
        }
      })

      this.canvas.on('object:moving', function (o) {
        o.target.setCoords()
        const target = o.target
        const widgetId = target.id
        const point = target.lineCoords
        const topLeftX = point.tl.x
        const topLeftY = point.tl.y
        const bottomRightX = point.br.x
        const bottomRightY = point.br.y
        if (o.target.get('type') === 'circle') {
          me.canvas.forEachObject(function (obj) {
            const pointer = o.pointer
            const circle = o.target
            if (obj === o.target) {
              obj.set('opacity', 1)
            } else if (circle.intersectsWithObject(obj) && obj.get('type') === 'stickyNote') {
              const { mtr, ...coordsWithoutMtr } = obj.oCoords
              const targerPoint = me.getCloestACrood(pointer, Object.values(coordsWithoutMtr))
              circle.connectedWidgetId = obj.id
              circle.set({ left: targerPoint.x, top: targerPoint.y })
              circle.setCoords()
              circle.fire('moving', {
                pointer: {
                  x: circle.left,
                  y: circle.top
                }
              })
              LinkLine(me.boardId, { endPoint: circle.endPoint, widgetId: circle.lineId, targetId: circle.connectedWidgetId })
              obj.on('moving', function (o) {
                const { mtr, ...coordsWithoutMtr } = obj.oCoords
                const targerPoint = me.getCloestACrood(pointer, Object.values(coordsWithoutMtr))
                circle.set({ left: targerPoint.x, top: targerPoint.y })
                circle.setCoords()
                circle.fire('moving', {
                  pointer: {
                    x: circle.left,
                    y: circle.top
                  }
                })
              })
              obj.on('moved', function (o) {
                circle.fire('moved', {
                  pointer: {
                    x: circle.left,
                    y: circle.top
                  }
                })
              })
            }
          })
        } else if (o.target.get('type') === 'stickyNote' && me.isSamplingWidgetDelayFinish) {
          me.isSamplingWidgetDelayFinish = false
          setTimeout(function () {
            me.isSamplingWidgetDelayFinish = true
            MoveStickyNoteBy(me.boardId, {
              [widgetId]: {
                topLeftX: topLeftX,
                topLeftY: topLeftY,
                bottomRightX: bottomRightX,
                bottomRightY: bottomRightY
              }
            })
          }, 200)
        } else if (o.target.get('type') === 'line' && me.isSamplingWidgetDelayFinish) {
          const line = o.target
          var strandsDistance = line.calcLinePoints()
          var lineCenterX = (topLeftX + bottomRightX) / 2
          var lineCenterY = (topLeftY + bottomRightY) / 2

          line.circleHead.set({ left: lineCenterX + strandsDistance.x1 - line.circleHead.radius, top: lineCenterY + strandsDistance.y1 - line.circleHead.radius })
          line.circleHead.setCoords()
          line.circleTail.set({ left: lineCenterX + strandsDistance.x2 - line.circleTail.radius, top: lineCenterY + strandsDistance.y2 - line.circleTail.radius })
          line.circleTail.setCoords()
          line.circleHead.fire('moving', {
            pointer: {
              x: lineCenterX + strandsDistance.x1,
              y: lineCenterY + strandsDistance.y1
            }
          })
          line.circleTail.fire('moving', {
            pointer: {
              x: lineCenterX + strandsDistance.x2,
              y: lineCenterY + strandsDistance.y2
            }
          })
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
    getCloestACrood (point, aCoords) {
      var result
      var leastDistance = 2000000
      aCoords.forEach(coord => {
        const distance = Math.sqrt(Math.pow(point.x - coord.x, 2) + Math.pow(point.y - coord.y, 2))
        if (distance < leastDistance) {
          leastDistance = distance
          result = coord
        }
      })
      return result
    },
    async deleteWidget () {
      let res = null
      if (this.selectedWidget.get('type') === 'stickyNote') {
        res = await DeleteStickyNoteBy(this.selectedWidget.id, this.boardId)
      } else if (this.selectedWidget.get('type') === 'line') {
        res = await DeleteLineBy(this.selectedWidget.id, this.boardId)
      }

      if (res !== null) {
        this.isDisplayRightClickMenu = false
        this.selectedWidget = null
      }
    },
    async loadAllStickyNote (widgets) {
      await widgets.forEach(widget => {
        this.boardContent.widgetDtos.push(widget)
        this.loadStickyNoteIntoCanvas(widget)
      })
      this.canvas.renderAll()
    },
    async loadAllLine (widgets) {
      await widgets.forEach(widget => {
        this.boardContent.widgetDtos.push(widget)
        this.loadLineIntoCanvas(widget)
      })
      this.canvas.renderAll()
    },
    async loadStickyNoteIntoCanvas (widgetDto) {
      await this.canvas.add(this.buildFabricObjectOfStickyNote(widgetDto))
      this.canvas.renderAll()
    },
    async loadLineIntoCanvas (widgetDto) {
      const ourLine = this.buildFabricObjectOfLine(widgetDto)
      await this.canvas.add(ourLine, ourLine.circleHead, ourLine.circleTail)
      this.canvas.renderAll()
    },
    setTarget (target) {
      if (target !== null) {
        this.selectedWidget = target
      } else {
        this.selectedWidget = null
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
          height: objects[0].height * objects[0].scaleY,
          width: objects[0].width * objects[0].scaleX,
          fill: objects[0].fill,
          text: objects[1].text,
          textColor: objects[1].fill
        }))
        canvas.getObjects().filter(object => object.get('connectedWidgetId') === group.id).forEach(circle => {
          me.connectCircleToWidget(circle)
        })
        canvas.renderAll()
        await EditTextOfStickyNoteBy(group.id, me.boardId, objects[1].text)
      })
    },
    async changeColorOfStickyNoteWith (color) {
      const res = await ChangeColorOfStickyNoteBy(this.selectedWidget.id, this.boardId, color)
      if (res !== null) {
        this.selectedWidget.rectObject.set('fill', color)
        this.canvas.renderAll()
      }
      this.isDisplayRightClickMenu = false
    },
    async bringToFront () {
      const topZOrder = this.canvas.getObjects().length - 1
      const circleCount = this.canvas.getObjects().filter(object => object.get('type') === 'circle').length
      const zOrder = topZOrder - circleCount
      await ChangeZOrderOfStickyNoteBy(this.selectedWidget.id, this.boardId, zOrder)
      this.isDisplayRightClickMenu = false
    },
    async sendToback () {
      const zOrder = 0
      await ChangeZOrderOfStickyNoteBy(this.selectedWidget.id, this.boardId, zOrder)
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
    connectCircleToWidget (circle) {
      if (circle.connectedWidgetId) {
        const pointer = { x: circle.left, y: circle.top }
        // TODO 如果widget在line後面才new，會爆掉
        const obj = this.canvas.getObjects().find(widget => widget.id === circle.connectedWidgetId)
        const { mtr, ...coordsWithoutMtr } = obj.oCoords
        const targerPoint = this.getCloestACrood(pointer, Object.values(coordsWithoutMtr))
        circle.connectedWidgetId = obj.id
        circle.set({ left: targerPoint.x, top: targerPoint.y })
        circle.setCoords()
        circle.fire('moving', {
          pointer: {
            x: circle.left,
            y: circle.top
          }
        })
        const me = this
        obj.on('moving', function (o) {
          const { mtr, ...coordsWithoutMtr } = obj.oCoords
          const targerPoint = me.getCloestACrood(pointer, Object.values(coordsWithoutMtr))
          circle.set({ left: targerPoint.x, top: targerPoint.y })
          circle.setCoords()
          circle.fire('moving', {
            pointer: {
              x: circle.left,
              y: circle.top
            }
          })
        })
        obj.on('moved', function (o) {
          circle.fire('moved', {
            pointer: {
              x: circle.left,
              y: circle.top
            }
          })
        })
      }
    },
    buildFabricObjectOfLine (widget) {
      const line = new fabric.OurLine({
        id: widget.widgetId,
        coors: {
          topLeftX: widget.topLeftX,
          topLeftY: widget.topLeftY,
          bottomRightX: widget.bottomRightX,
          bottomRightY: widget.bottomRightY
        },
        headWidgetId: widget.headWidgetId,
        tailWidgetId: widget.tailWidgetId
      }, {
        fill: 'red',
        stroke: 'black',
        strokeWidth: 5,
        selectable: true,
        evented: true
      })

      const me = this
      line.circleHead.on('moving', function (e) {
        line.set({ x1: line.circleHead.left, y1: line.circleHead.top })
        line.setCoords()
        if (me.isSamplingLineDelayFinish) {
          me.isSamplingLineDelayFinish = false
          setTimeout(function () {
            me.isSamplingLineDelayFinish = true
            MoveLineBy(me.boardId, {
              [line.id]: {
                topLeftX: line.x1,
                topLeftY: line.y1,
                bottomRightX: line.x2,
                bottomRightY: line.y2
              }
            })
          }, 200)
        }
      })

      line.circleTail.on('moving', function (e) {
        line.set({ x2: line.circleTail.left, y2: line.circleTail.top })
        line.setCoords()
        if (me.isSamplingLineDelayFinish) {
          me.isSamplingLineDelayFinish = false
          setTimeout(function () {
            me.isSamplingLineDelayFinish = true
            MoveLineBy(me.boardId, {
              [line.id]: {
                topLeftX: line.x1,
                topLeftY: line.y1,
                bottomRightX: line.x2,
                bottomRightY: line.y2
              }
            })
          }, 200)
        }
      })

      this.connectCircleToWidget(line.circleHead)
      this.connectCircleToWidget(line.circleTail)

      line.circleHead.on('moved', function (e) {
        setTimeout(function () {
          MoveLineBy(me.boardId, {
            [line.id]: {
              topLeftX: line.x1,
              topLeftY: line.y1,
              bottomRightX: line.x2,
              bottomRightY: line.y2
            }
          })
        }, 200)

        const lineHeadPointer = { x: line.x1, y: line.y1 }
        me.canvas.forEachObject(function (obj) {
          if (obj.id === line.circleHead.connectedWidgetId) {
            const { mtr, ...coordsWithoutMtr } = obj.oCoords
            const connectedCrood = me.getCloestACrood(lineHeadPointer, Object.values(coordsWithoutMtr))
            const connectedPointer = { x: connectedCrood.x, y: connectedCrood.y }
            const deviation = 2
            if (Math.abs(connectedPointer.x - lineHeadPointer.x) > deviation || Math.abs(connectedPointer.y - lineHeadPointer.y) > deviation) {
              DisconnectLine(me.boardId, {
                lineId: line.id,
                endPoint: 'head'
              })
              return false
            }
          }
        })
      })

      line.circleTail.on('moved', function (e) {
        setTimeout(function () {
          MoveLineBy(me.boardId, {
            [line.id]: {
              topLeftX: line.x1,
              topLeftY: line.y1,
              bottomRightX: line.x2,
              bottomRightY: line.y2
            }
          })
        }, 200)

        const lineTailPointer = { x: line.x2, y: line.y2 }
        me.canvas.forEachObject(function (obj) {
          if (obj.id === line.circleTail.connectedWidgetId) {
            const { mtr, ...coordsWithoutMtr } = obj.oCoords
            const connectedCrood = me.getCloestACrood(lineTailPointer, Object.values(coordsWithoutMtr))
            const connectedPointer = { x: connectedCrood.x, y: connectedCrood.y }
            const deviation = 2
            if (Math.abs(connectedPointer.x - lineTailPointer.x) > deviation || Math.abs(connectedPointer.y - lineTailPointer.y) > deviation) {
              DisconnectLine(me.boardId, {
                lineId: line.id,
                endPoint: 'tail'
              })
              return false
            }
          }
        })
      })
      return line
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
