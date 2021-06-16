import { fabric } from 'fabric'
import Note from './note.js'
import Line from './line.js'
import miroAPI from '@/utils/apis.js'
import { uuid } from 'vue-uuid'

export default fabric.util.createClass(fabric.Canvas, {
  boardId: '',
  distance: 10,
  figures: {},
  initialize: initialize,
  drawGridFrame: drawGridFrame,
  getFigure: getFigure,
  postNote: postNote,
  addNote: addNote,
  moveNote: moveNote,
  resizeNote: resizeNote,
  changeNoteText: changeNoteText,
  changeNoteColor: changeNoteColor,
  drawLine: drawLine,
  addLine: addLine,
  moveLine: moveLine,
  moveLineEndpoint: moveLineEndpoint,
  connectLineEndpointToFigure: connectLineEndpointToFigure,
  disconnectLineEndpoint: disconnectLineEndpoint,
  removeFigure: removeFigure
})

function initialize (id, width, height, boardId) {
  console.log(boardId)
  this.callSuper('initialize', id, {
    width: width,
    height: height
  })
  this.boardId = boardId
  this.selection = false

  const moveCanvasInfo = {
    isDragging: false,
    lastX: 0,
    lastY: 0
  }

  this.on('mouse:down', (e) => {
    console.log(e.e.altKey)
    if (e.e.altKey) {
      moveCanvasInfo.isDragging = true
      this.selection = false
      moveCanvasInfo.lastX = e.e.clientX
      moveCanvasInfo.lastY = e.e.clientY
    }
  })

  this.on('mouse:move', (e) => {
    if (moveCanvasInfo.isDragging) {
      // 同 canvas transform method
      // 計算移動量
      this.viewportTransform[4] += e.e.clientX - moveCanvasInfo.lastX
      this.viewportTransform[5] += e.e.clientY - moveCanvasInfo.lastY
      this.requestRenderAll()
      moveCanvasInfo.lastX = e.e.clientX
      moveCanvasInfo.lastY = e.e.clientY
    }
  })

  this.on('mouse:up', (opt) => {
    moveCanvasInfo.isDragging = false
    moveCanvasInfo.selection = true
  })
  this.drawGridFrame()
}

function getFigure (figureId) {
  return this.figures[figureId]
}

async function postNote (left, top, width, height, color) {
  const figureId = await miroAPI.note.postNote(this.boardId, left, top, width, height, color)
  this.addNote(figureId, left, top, width, height, color, '')
}

function addNote (figureId, left, top, width, height, color, text) {
  if (this.figures[figureId] !== undefined) {
    return
  }
  const note = new Note(figureId, left, top, width, height, color, text)
  this.figures[figureId] = note
  this.add(note)
}

function moveNote (figureId, left, top) {
  const note = this.getFigure(figureId)
  if (note !== undefined && note.isType('note')) {
    note.movePosition(left, top)
  }
}

function resizeNote (figureId, width, height) {
  const note = this.getFigure(figureId)
  if (note !== undefined && note.isType('note')) {
    note.resize(width, height)
  }
}

function changeNoteText (figureId, text) {
  const note = this.getFigure(figureId)
  if (note !== undefined && note.isType('note')) {
    note.changeText(text)
  }
}

function changeNoteColor (figureId, color, callAPI) {
  const note = this.getFigure(figureId)
  if (note !== undefined && note.isType('note')) {
    note.changeColor(color, callAPI)
  }
}

async function drawLine (endpointA, endpointB) {
  endpointA.id = uuid.v4()
  endpointB.id = uuid.v4()
  const figureId = await miroAPI.line.drawLine(this.boardId, endpointA, endpointB)
  console.log(figureId)
  this.addLine(figureId, endpointA, endpointB)
}

function addLine (figureId, endpointA, endpointB) {
  if (this.figures[figureId] !== undefined) {
    return
  }
  const line = new Line(figureId, endpointA, endpointB)
  this.figures[figureId] = line
  this.add(line)
  this.renderAll()
}

function moveLine (figureId, offsetX, offsetY) {
  const line = this.getFigure(figureId)

  if (line === undefined || !line.isType('line')) {
    return
  }

  line.move(offsetX, offsetY)
}

function moveLineEndpoint (figureId, endpointId, positionX, positionY) {
  const line = this.getFigure(figureId)

  if (line === undefined || !line.isType('line')) {
    return
  }

  line.moveEndpoint(endpointId, positionX, positionY)
}

function removeFigure (figureId) {
  this.remove(this.figures[figureId])
  delete this.figures[figureId]
}

function connectLineEndpointToFigure (figureId, endpointId, connectedFigureId) {
  const line = this.getFigure(figureId)
  if (line === undefined || !line.isType('line')) {
    return
  }

  line.connectLineEndpointToFigure(endpointId, connectedFigureId)
}

function disconnectLineEndpoint (figureId, endpointId) {
  const line = this.getFigure(figureId)
  if (line === undefined || !line.isType('line')) {
    return
  }

  line.disconnectLineEndpoint(endpointId)
}

function drawGridFrame () {
  this.renderOnAddRemove = false
  const longer = 12000
  let vLine
  let hLine
  const distance = this.distance
  for (let i = 1; i * distance < longer; i++) {
    const lineDef = {
      fill: 'black',
      stroke: 'rgba(0, 0, 0, 0.1)',
      strokeWidth: 1,
      selectable: false
    }
    // draw vLine
    if (i * distance >= 8000 && i % 5 === 0) {
      vLine = new fabric.Line([i * distance, 0, i * distance, 8000], lineDef)
      // if (i % 5 === 0) {
      vLine.stroke = 'rgba(0, 0, 0, 0.7)'
      // }
      this.add(vLine)
    }
    // draw hLine
    if (i * distance < 3000 && i % 5 === 0) {
      hLine = new fabric.Line([8000, i * distance, this.width, i * distance], lineDef)
      // if (i % 5 === 0) {
      hLine.stroke = 'rgba(0, 0, 0, 0.7)'
      // }
      this.add(hLine)
    }
  }
  this.renderOnAddRemove = true
  this.renderAll()
}
