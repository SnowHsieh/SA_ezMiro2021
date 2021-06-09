import { fabric } from 'fabric'
import { note as noteAPI } from '@/utils/apis.js'

export default fabric.util.createClass(fabric.Group, {
  type: 'note',
  figureId: '',
  rect: null,
  textbox: null,
  textOffsetLeft: 0,
  textOffsetTop: 0,
  _prevObjectStacking: null,
  initialize: initialize,
  _registerEventListener: _registerEventListener,
  _registerMovedEvent: _registerMovedEvent,
  _registerResizedEvent: _registerResizedEvent,
  _registerDeletedEvent: _registerDeletedEvent,
  _registerEditTextEvent: _registerEditTextEvent,
  _registerMouseEvent: _registerMouseEvent,
  _registerSelectedEvent: _registerSelectedEvent,
  _setControlsVisible: _setControlsVisible,
  _recalcTextboxFontSize: _recalcTextboxFontSize,
  _recalcTextboxPosition: _recalcTextboxPosition,
  movePosition: movePosition,
  resize: resize,
  changeText: changeText,
  changeColor: changeColor
})

function initialize (figureId, x, y, width, height, color, text) {
  this.callSuper('initialize', [], {})

  this.figureId = figureId

  this.rect = new fabric.Rect({
    left: x,
    top: y,
    width: width,
    height: height,
    stroke: color,
    fill: color,
    selectable: false,
    evented: false
  })

  this.textbox = new fabric.Textbox(text, {
    left: this.rect.left + 20,
    top: this.rect.top + 20,
    width: this.rect.width - 40,
    fill: '#000',
    textAlign: 'center',
    selectable: false,
    evented: false
  })

  this.addWithUpdate(this.rect)
  this.addWithUpdate(this.textbox)

  this.textOffsetLeft = this.textbox.left - this.rect.left
  this.textOffsetTop = this.textbox.top - this.rect.top
  this._recalcTextboxPosition()
  this._setControlsVisible()
  this._registerEventListener()
}

function _setControlsVisible () {
  this.setControlsVisibility({
    bl: true,
    br: true,
    mb: false,
    ml: false,
    mr: false,
    mt: false,
    tl: true,
    tr: true,
    mtr: false
  })
  this.textbox.hasControls = false
  this.textbox.hasBorders = false
}

function _recalcTextboxPosition () {
  this._recalcTextboxFontSize()

  const rectLeftTop = this.rect.getPointByOrigin('left', 'top')
  this.textbox.set('width', this.textbox.getScaledWidth() * 0.8)
  this.textbox.set('left', rectLeftTop.x + this.rect.getScaledWidth() / 2 - this.textbox.getScaledWidth() / 2)
  this.textbox.set('top', rectLeftTop.y + this.rect.getScaledHeight() / 2 - this.textbox.getScaledHeight() / 2)
}

function _recalcTextboxFontSize () {
  if (this.textbox.text !== '') {
    let lineNumber = 0
    let maxLineTextWidth = 0

    this.textbox._textLines.forEach(() => {
      const lineTextWidth = this.textbox.getLineWidth(lineNumber)
      if (lineTextWidth > maxLineTextWidth) {
        maxLineTextWidth = lineTextWidth
      }
      lineNumber += 1
    })
    this.textbox.width = maxLineTextWidth

    const maxFixedWidth = this.rect.getScaledWidth() * 0.85
    const maxFiexdHeight = this.rect.getScaledHeight() * 0.85
    const maxFontSize = this.rect.getScaledHeight().height * 0.85

    let newFontSize = this.textbox.fontSize

    newFontSize *= maxFixedWidth / (this.textbox.width + 1)

    this.textbox.width = maxFixedWidth
    this.textbox.set('fontSize', newFontSize)
    while (this.textbox.height > maxFiexdHeight) {
      const scale = this.textbox.height / maxFiexdHeight
      if (newFontSize > maxFontSize) {
        newFontSize = maxFontSize
      }
      newFontSize -= scale

      this.textbox.set('fontSize', newFontSize)
    }

    this.textbox.set('fontSize', newFontSize)
  }
}

function _registerEventListener () {
  this._registerMovedEvent()
  this._registerResizedEvent()
  this._registerDeletedEvent()
  this._registerEditTextEvent()
  this._registerMouseEvent()
  this._registerSelectedEvent()
}

function movePosition (left, top) {
  this.set({
    left: left,
    top: top
  })
  this.addWithUpdate()
  this._recalcTextboxPosition()
}

function resize (width, height) {
  this.rect.set({
    width: width,
    height: height
  })
  this.addWithUpdate()
  this._recalcTextboxPosition()
}

function changeText (text) {
  this.textbox.set({
    text: text
  })
  this._recalcTextboxPosition()
}

function changeColor (color) {
  this.rect.set({
    stroke: color,
    fill: color
  })
  noteAPI.changeNoteColor(this.figureId, color)
}

function _registerMovedEvent () {
  let timerId = ''

  this.on('moving', (e) => {
    this._recalcTextboxPosition()

    if (timerId === '') {
      timerId = setInterval(() => {
        console.log(`${this.left} ${this.top}`)
        noteAPI.moveNote(this.figureId, this.left, this.top)
      }, 200)
    }
  })

  this.on('moved', (e) => {
    clearInterval(timerId)
    timerId = ''
    noteAPI.moveNote(this.figureId, this.left, this.top)
  })
}

function _registerResizedEvent () {
  let timerId = ''

  this.on('scaling', () => {
    this._recalcTextboxPosition()

    if (timerId === '') {
      timerId = setInterval(() => {
        noteAPI.moveNote(this.figureId, this.left, this.top)
        noteAPI.resizeNote(this.figureId, this.getScaledWidth(), this.getScaledHeight())
      }, 200)
    }
  })

  this.on('scaled', () => {
    clearInterval(timerId)
    timerId = ''
    noteAPI.resizeNote(this.figureId, this.getScaledWidth(), this.getScaledHeight())
    noteAPI.moveNote(this.figureId, this.left, this.top)
  })
}

function _registerDeletedEvent () {
  this.on('removed', () => {
    // noteAPI.deleteNote(this.figureId)
  })
}

function _registerEditTextEvent () {
  this.textbox.on('changed', () => {
    this._recalcTextboxPosition()
    noteAPI.editNoteText(this.figureId, this.textbox.text)
  })

  this.textbox.on('editing:exited', () => {
    this.textbox.selectable = false
    this.textbox.evented = false
    this.selectable = true
  })
}

function _registerMouseEvent () {
  this.on('mousedown:before', () => {
    this._prevObjectStacking = this.canvas.preserveObjectStacking
    this.canvas.preserveObjectStacking = true
  })

  this.on('mousedblclick', () => {
    this.textbox.selectable = true
    this.textbox.evented = true
    this.canvas.setActiveObject(this.textbox)
    this.textbox.enterEditing()
    this.selectable = false
  })
}

function _registerSelectedEvent () {
  this.on('selected', () => {

  })

  this.on('deselected', () => {
    this.canvas.preserveObjectStacking = this._prevObjectStacking
  })
}
