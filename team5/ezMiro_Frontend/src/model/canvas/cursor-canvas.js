import { fabric } from 'fabric'
import cursorSVG from '@/assets/svg/cursor-default.svg'
// import { Line } from 'fabric/fabric-impl'
// import miroAPI from '@/utils/apis.js'

export default fabric.util.createClass(fabric.Canvas, {
  type: 'UserCanvas',
  boardId: '',
  cursors: {},
  initialize: initialize,
  addCursor: addCursor,
  removeCursor: removeCursor,
  moveCursor: moveCursor,
  getCursor: getCursor
})

function initialize (id, width, height, boardId) {
  this.callSuper('initialize', id, {
    width: width,
    height: height
  })
  this.boardId = boardId
}

function addCursor (userId) {
  const cursor = new Cursor(userId, 300, 300)
  this.cursors[userId] = cursor
  this.add(cursor)
}

function removeCursor (userId) {
  this.remove(this.cursors[userId])
  delete this.cursors[userId]
  this.renderAll()
}

function moveCursor (userId, x, y) {
  if (this.cursors[userId] === undefined) {
    return
  }
  this.cursors[userId].set({
    left: x,
    top: y
  })
  this.renderAll()
}

function getCursor (userId) {
  return this.cursors[userId]
}

const Cursor = fabric.util.createClass(fabric.Group, {
  typs: 'cursor',
  userId: '',
  initialize: function (userId, x, y) {
    this.callSuper('initialize', [], {
      visible: false,
      originX: 'left',
      originY: 'top'
    })

    this.userId = userId

    fabric.loadSVGFromURL(cursorSVG, (objects, options) => {
      const loadedObject = fabric.util.groupSVGElements(objects, options)
      console.log(options)
      this.addWithUpdate(loadedObject)
      this.addWithUpdate(new fabric.IText(this.userId, {
        left: 20,
        top: 10,
        fontSize: 14
      }))
      this.set({
        visible: true,
        left: x,
        top: y
      })
      this.canvas.renderAll()

      console.log(this)
    })
  }
})
