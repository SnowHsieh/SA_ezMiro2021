<template>
  <div>
    <v-card
      @contextmenu="show"
    >
      <div class="absolute">
        <canvas id="canvas" class="test"/>
      </div>
      <div class="absolute active_cursors_canvas">
        <canvas id="active_cursors_canvas"/>
      </div>
    </v-card>
    <v-menu
      v-model="showMenu"
      :position-x="x"
      :position-y="y"
      absolute
      offset-y
    >
      <div class="d-flex flex-column">
        <v-menu
          top
          :offset-x="true"
          open-on-hover
          v-if="showChangeNoteMenu"
        >
          <template v-slot:activator="{ on, attrs }">
            <v-btn
              v-bind="attrs"
              v-on="on"
            >
              change color
            </v-btn>
          </template>
          <div class="d-flex flex-column">
            <v-btn
              v-for="(item, index) in items"
              :key="index"
              @click="changeNoteColor(item.color)"
            >
              <font-awesome-icon size="2x" :icon="['fas', 'sticky-note']" :style="{ color: item.color }"/>
            </v-btn>
          </div>
        </v-menu>
        <v-btn @click="bringToFront">bring to front</v-btn>
        <v-btn @click="sendToBack">send to back</v-btn>
      </div>
    </v-menu>
  </div>
</template>
<script>
import { markRaw } from '@vue/reactivity'
import Canvas from '@/model/canvas/canvas.js'
import CursorCanvas from '@/model/canvas/cursor-canvas.js'
import eventbus from '@/utils/eventbus.js'
import event from './event/event.js'
import miroApi from '@/utils/apis.js'

export default {
  name: 'EzMiroCanvas',
  props: ['boardId'],
  data: () => ({
    showMenu: false,
    showChangeNoteMenu: false,
    x: 0,
    y: 0,
    toggle_one: 0,
    items: [
      { title: 'Read Model', color: '#CADF58' },
      { title: 'Command', color: '#6CD5F5' },
      { title: 'Domain Event', color: '#FD9E4B' },
      { title: 'Policy', color: '#C08AC9' },
      { title: 'Aggregate', color: '#FFF9B2' }
    ],
    canvas: null,
    activeCursorCanvas: null,
    mousePosition: {
      x: 0,
      y: 0
    },
    activeObject: null,
    noteColor: '#CADF58',
    webSocket: null,
    canvasToolMode: event.canvasToolMode.mouseModel
  }),
  async mounted () {
    console.log(this.boardId)
    const boardId = this.boardId
    this.canvas = markRaw(new Canvas('canvas', 6000, 6000, boardId))
    this.activeCursorCanvas = markRaw(new CursorCanvas('active_cursors_canvas', 6000, 6000))
    this.registerEventBus()
    this.registerCanvasEvent()
    this.registerWindowEvent()
    this.getBoardContent()
    this.setWebSocket()
  },
  methods: {
    show (e) {
      e.preventDefault()
      const activeObjects = this.canvas.getActiveObjects()
      if (activeObjects.length === 1) {
        if (activeObjects[0].isType('note') || activeObjects[0].isType('line')) {
          if (activeObjects[0].isType('note')) {
            this.showChangeNoteMenu = true
          } else {
            this.showChangeNoteMenu = false
          }
          this.activeObject = activeObjects[0]
          this.showMenu = false
          this.x = e.clientX
          this.y = e.clientY
          this.$nextTick(() => {
            this.showMenu = true
          })
        }
      }
    },
    registerEventBus () {
      eventbus.on(event.canvasToolMode.mouseMode, () => {
        this.canvasToolMode = event.canvasToolMode.mouseMode
      })
      eventbus.on(event.canvasToolMode.postNoteMode, (e) => {
        this.noteColor = e.color
        this.canvasToolMode = event.canvasToolMode.postNoteMode
      })
      eventbus.on(event.canvasToolMode.drawLineMode, () => {
        this.canvasToolMode = event.canvasToolMode.drawLineMode
      })
    },
    registerCanvasEvent () {
      this.canvas.on('mouse:move', (e) => {
        this.mouseMoveEvent(e)
      })
      this.canvas.on('mouse:down', (e) => {
        this.mouseDownEvent(e)
      })
    },
    registerWindowEvent () {
      window.addEventListener('keydown', (e) => {
        if (e.key === 'Delete') {
          this.canvas.getActiveObjects().forEach((target) => {
            if (target.isType('note')) {
              this.canvas.removeFigure(target.figureId)
              miroApi.note.deleteNote(target.figureId)
            } else if (target.isType('line')) {
              this.canvas.removeFigure(target.figureId)
              miroApi.line.deleteLine(target.figureId)
            }
          })
        }
      })
    },
    mouseMoveEvent (e) {
      this.mousePosition.x = e.pointer.x
      this.mousePosition.y = e.pointer.y
      if (this.canvasToolMode === event.canvasToolMode.mouseMode) {

      } else if (this.canvasToolMode === event.canvasToolMode.postNoteMode) {

      }
    },
    mouseDownEvent (e) {
      if (this.canvasToolMode === event.canvasToolMode.postNoteMode) {
        eventbus.emit(event.canvasEvent.mouseDown)
        this.canvas.postNote(e.pointer.x, e.pointer.y, 100, 100, this.noteColor)
      } else if (this.canvasToolMode === event.canvasToolMode.drawLineMode) {
        eventbus.emit(event.canvasEvent.mouseDown)
        this.canvas.drawLine({ positionX: e.pointer.x, positionY: e.pointer.y, connectedFigureId: '' }, { positionX: e.pointer.x + 100, positionY: e.pointer.y + 100, connectedFigureId: '' })
      }
    },
    async getBoardContent () {
      console.log('getBoardContent')
      const boardContent = await miroApi.board.getBoardContent(this.boardId)
      this.canvas.clear()
      boardContent.forEach(figure => {
        console.log(figure)
        if (figure.figureType === 'NOTE') {
          this.canvas.addNote(
            figure.figureId,
            figure.leftTopPositionX,
            figure.leftTopPositionY,
            figure.width,
            figure.height,
            figure.color,
            figure.text
          )
        } else if (figure.figureType === 'LINE') {
          this.canvas.addLine(
            figure.figureId,
            figure.endpointA,
            figure.endpointB
          )
        }
      })
      this.canvas.renderAll()
      this.canvas.fire('finish_loading')
    },
    setWebSocket () {
      var sendCursorTimerId = ''

      const preMousePosition = {
        x: 0,
        y: 0
      }
      this.user = {
        name: `匿名使用者${Math.floor(Math.random() * 1000) + 1}`
      }
      this.webSocket = new WebSocket(
        `ws://localhost:8080/WebSocketServer/${this.boardId}/${this.user.name}`
      )

      this.webSocket.onopen = (e) => {
        console.log('WebSocket connected.')
      }

      this.webSocket.onclose = (e) => {
        console.log(e)
        clearInterval(sendCursorTimerId)
      }

      this.webSocket.onmessage = async (e) => {
        const data = await JSON.parse(e.data)
        if (data.userId === this.user.name) {
          return
        }

        const isActive = this.canvas.getActiveObjects().some((target) => {
          if (target.isType('endpoint')) {
            return target.line.figureId === data.figureId
          }
          return target.figureId === data.figureId
        })
        if (isActive) {
          return
        }

        if (data.type === 'BoardEntered') {
          this.activeCursorCanvas.addCursor(data.userId)
          this.webSocket.send(
            JSON.stringify({ x: this.mousePosition.x, y: this.mousePosition.y })
          )
        } else if (data.type === 'CursorMoved') {
          const userCursor = this.activeCursorCanvas.getCursor(data.userId)

          if (userCursor === undefined) {
            this.activeCursorCanvas.addCursor(data.userId)
          }

          this.activeCursorCanvas.moveCursor(
            data.userId,
            data.positionX,
            data.positionY
          )
        } else if (data.type === 'BoardLeft') {
          this.activeCursorCanvas.removeCursor(data.userId)
        } else if (data.type === 'NotePosted') {
          this.canvas.addNote(data.figureId, data.leftTopPositionX, data.leftTopPositionY, data.width, data.height, data.color, '')
        } else if (data.type === 'NoteMoved') {
          this.canvas.moveNote(data.figureId, data.newLeftTopPositionX, data.newLeftTopPositionY)
        } else if (data.type === 'NoteColorChanged') {
          this.canvas.changeNoteColor(data.figureId, data.newColor, false)
        } else if (data.type === 'NoteResized') {
          this.canvas.resizeNote(data.figureId, data.newWidth, data.newHeight)
        } else if (data.type === 'NoteTextEdited') {
          this.canvas.changeNoteText(data.figureId, data.newText)
        } else if (data.type === 'FigureDeleted') {
          this.canvas.removeFigure(data.figureId)
        } else if (data.type === 'LineDrew') {
          this.canvas.addLine(data.figureId, data.endpointA, data.endpointB)
        } else if (data.type === 'LineMoved') {
          this.canvas.moveLine(data.figureId, data.offsetX, data.offsetY)
        } else if (data.type === 'LineEndpointMoved') {
          this.canvas.moveLineEndpoint(data.figureId, data.endpointId, data.newPositionX, data.newPositionY)
        } else if (data.type === 'LineConnected') {
          this.canvas.connectLineEndpointToFigure(data.figureId, data.endpointId, data.connectedFigureId)
        }
        this.canvas.renderAll()
      }
      sendCursorTimerId = setInterval(() => {
        if (preMousePosition.x === this.mousePosition.x &&
         preMousePosition.y === this.mousePosition.y) {
          return
        }
        this.webSocket.send(
          JSON.stringify({ x: this.mousePosition.x, y: this.mousePosition.y })
        )
        preMousePosition.x = this.mousePosition.x
        preMousePosition.y = this.mousePosition.y
      }, 200)
    },
    changeNoteColor (color) {
      if (this.activeObject.isType('note')) {
        this.canvas.changeNoteColor(this.activeObject.figureId, color, true)
        this.canvas.renderAll()
      }
    },
    bringToFront () {
      this.canvas.bringToFront(this.activeObject)
      miroApi.board.bringFigureToFront(this.boardId, this.activeObject.figureId)
    },
    sendToBack () {
      this.canvas.sendToBack(this.activeObject)
      miroApi.board.sendFigureToBack(this.boardId, this.activeObject.figureId)
    }
  }
}
</script>
<style scoped>
.active_cursors_canvas {
  pointer-events: none;
}
.absolute {
  position:absolute !important;
}
</style>
