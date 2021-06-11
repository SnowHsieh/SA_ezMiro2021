import { fabric } from 'fabric'
import Note from './note.js'
import Line from './line.js'
import miroAPI from '@/utils/apis.js'
import { uuid } from 'vue-uuid'

export default fabric.util.createClass(fabric.Canvas, {
  boardId: '',
  figures: {},
  initialize: initialize,
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
}

function getFigure (figureId) {
  return this.figures[figureId]
}

async function postNote (left, top, width, height, color) {
  const figureId = miroAPI.note.postNote(this.boardId, left, top, width, height, color)
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
