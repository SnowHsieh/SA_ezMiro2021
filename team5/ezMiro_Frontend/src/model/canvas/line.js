import { fabric } from 'fabric'
import { line as lineAPI } from '@/utils/apis.js'

export default fabric.util.createClass(fabric.Line, {
  type: 'line',
  figureId: '',
  endpointA: null,
  endpointB: null,
  initialize: initialize,
  _registerEventListener: _registerEventListener,
  _registerAddedEvent: _registerAddedEvent,
  _registerMovedEvent: _registerMovedEvent,
  _registerDeletedEvent: _registerDeletedEvent,
  move: move,
  moveEndpoint: moveEndpoint,
  connectLineEndpointToFigure: connectLineEndpointToFigure,
  disconnectLineEndpoint: disconnectLineEndpoint,
  setEndpointWhenConnected: setEndpointWhenConnected,
  setEndpointWhenDisconnected: setEndpointWhenDisconnected
})

function initialize (figureId, endpointA, endpointB) {
  this.figureId = figureId

  const positions = [
    endpointA.positionX,
    endpointA.positionY,
    endpointB.positionX,
    endpointB.positionY
  ]

  this.callSuper('initialize', positions, {
    stroke: 'black',
    strokeWidth: 5,
    selectable: true,
    evented: true
  })

  this.hasControls = false
  this.hasBorders = false

  this.endpointA = new Endpoint(endpointA, this)
  this.endpointB = new Endpoint(endpointB, this)

  this.on('added', () => {
    this.canvas.add(this.endpointA)
    this.canvas.add(this.endpointB)
  })

  this._registerEventListener()
}

const Endpoint = fabric.util.createClass(fabric.Circle, {
  type: 'endpoint',
  id: '',
  connectedFigureId: '',
  line: null,
  connectedFigure: null,
  _handleFigureMoving: null,
  initialize (endpoint, line) {
    this.callSuper('initialize', {
      left: endpoint.positionX,
      top: endpoint.positionY,
      strokeWidth: 1,
      radius: 8,
      fill: '#ffffff',
      stroke: '#666666',
      originX: 'center',
      originY: 'center',
      selectable: true,
      evented: true
    })
    this.id = endpoint.id
    this.connectedFigureId = endpoint.connectedFigureId
    this.line = line
    this.hasBorders = false
    this.hasControls = false
  }
})

function _registerEventListener () {
  this._registerMovedEvent()
  this._registerDeletedEvent()
  this._registerAddedEvent()
}

function _registerAddedEvent () {
  this.on('added', () => {
    this.canvas.on('finish_loading', () => {
      if (this.endpointA.connectedFigureId !== '') {
        const connectedFigure = this.canvas.getFigure(this.endpointA.connectedFigureId)
        this.setEndpointWhenConnected(this.endpointA, connectedFigure)
      }
      if (this.endpointB.connectedFigureId !== '') {
        const connectedFigure = this.canvas.getFigure(this.endpointB.connectedFigureId)
        this.setEndpointWhenConnected(this.endpointB, connectedFigure)
      }
      this.canvas.renderAll()
    })
  })
}

function _registerMovedEvent () {
  let timerId = ''
  const prePoint = this.getPointByOrigin('left', 'top')

  this.on('moving', () => {
    this.set(getPositions.call(this))
    this.endpointA.set({ left: this.x1, top: this.y1, visible: false })
    this.endpointB.set({ left: this.x2, top: this.y2, visible: false })
    this.endpointA.setCoords()
    this.endpointB.setCoords()
    if (timerId === '') {
      timerId = setInterval(() => {
        lineAPI.moveLine(this.figureId, this.left - prePoint.x, this.top - prePoint.y)
        prePoint.x = this.left
        prePoint.y = this.top
      }, 200)
    }
  })

  this.on('moved', () => {
    console.log('line moved')
    this.set(getPositions.call(this))
    this.endpointA.set({ left: this.x1, top: this.y1, visible: true })
    this.endpointB.set({ left: this.x2, top: this.y2, visible: true })
    this.endpointA.setCoords()
    this.endpointB.setCoords()
    lineAPI.moveLine(this.figureId, this.left - prePoint.x, this.top - prePoint.y)
    clearInterval(timerId)
  })

  this.endpointA.on('moving', () => {
    this.set({
      x1: this.endpointA.left,
      y1: this.endpointA.top
    })
    this.setCoords()
    // lineAPI.moveLineEndpoint(this.figureId, this.endpointA.id, this.endpointA.left, this.endpointA.top)
  })

  this.endpointA.on('moved', async () => {
    const canvasObjects = this.canvas.getObjects()
    let connectedFigure = null
    for (let i = canvasObjects.length - 1; i >= 0; i--) {
      const target = canvasObjects[i]
      if (target.isType('note') && this.endpointA.intersectsWithObject(target)) {
        connectedFigure = target
        break
      }
    }
    if (this.endpointA.connectedFigure !== connectedFigure) {
      if (this.endpointA.connectedFigure !== null) {
        this.setEndpointWhenDisconnected(this.endpointA)
        await lineAPI.disconnectLine(this.figureId, this.endpointA.id)
      }
      if (connectedFigure !== null) {
        this.setEndpointWhenConnected(this.endpointA, connectedFigure)
        await lineAPI.connectLine(this.figureId, this.endpointA.id, connectedFigure.figureId)
      }
    }
    if (this.endpointA.connectedFigure !== null) {
      this.endpointA._handleFigureMoving()
      this.canvas.renderAll()
    }
    if (this.endpointA.connectedFigure === null && this.endpointB.connectedFigure === null) {
      this.set({
        evented: true
      })
    }
    lineAPI.moveLineEndpoint(this.figureId, this.endpointA.id, this.endpointA.left, this.endpointA.top)
  })

  this.endpointB.on('moving', () => {
    this.set({
      x2: this.endpointB.left,
      y2: this.endpointB.top
    })
    this.setCoords()
    // lineAPI.moveLineEndpoint(this.figureId, this.endpointB.id, this.endpointB.left, this.endpointB.top)
  })

  this.endpointB.on('moved', async () => {
    const canvasObjects = this.canvas.getObjects()
    let connectedFigure = null
    for (let i = canvasObjects.length - 1; i >= 0; i--) {
      const target = canvasObjects[i]
      if (target.isType('note') && this.endpointB.intersectsWithObject(target)) {
        connectedFigure = target
        break
      }
    }
    if (this.endpointB.connectedFigure !== connectedFigure) {
      if (this.endpointB.connectedFigure !== null) {
        this.setEndpointWhenDisconnected(this.endpointB)
        await lineAPI.disconnectLine(this.figureId, this.endpointB.id)
      }
      if (connectedFigure !== null) {
        this.setEndpointWhenConnected(this.endpointB, connectedFigure)
        await lineAPI.connectLine(this.figureId, this.endpointB.id, connectedFigure.figureId)
      }
    }
    if (this.endpointB.connectedFigure !== null) {
      this.endpointB._handleFigureMoving()
      this.canvas.renderAll()
    }
    if (this.endpointA.connectedFigure === null && this.endpointB.connectedFigure === null) {
      this.set({
        evented: true
      })
    }
    lineAPI.moveLineEndpoint(this.figureId, this.endpointB.id, this.endpointB.left, this.endpointB.top)
  })
}

function _registerDeletedEvent () {
  this.on('removed', () => {
    this.canvas.remove(this.endpointA)
    this.canvas.remove(this.endpointB)
    // noteAPI.deleteNote(this.figureId)
  })
}

function getPositions () {
  let x1 = 0
  let y1 = 0
  let x2 = 0
  let y2 = 0

  if (this.endpointA.left < this.endpointB.left) {
    x1 = this.left
    x2 = this.left + this.width
  } else {
    x1 = this.left + this.width
    x2 = this.left
  }

  if (this.endpointA.top < this.endpointB.top) {
    y1 = this.top
    y2 = this.top + this.height
  } else {
    y1 = this.top + this.height
    y2 = this.top
  }

  return { x1: x1, y1: y1, x2: x2, y2: y2 }
}
// websocket
function move (offsetX, offsetY) {
  if (this.endpointA.connectedFigure !== null || this.endpointB.connectedFigure !== null) {
    return
  }

  const positions = getPositions.call(this)
  this.set({
    x1: positions.x1 + offsetX,
    x2: positions.x2 + offsetX,
    y1: positions.y1 + offsetY,
    y2: positions.y2 + offsetY
  })
  this.endpointA.set({ left: this.x1, top: this.y1, visible: true })
  this.endpointB.set({ left: this.x2, top: this.y2, visible: true })
  this.endpointA.setCoords()
  this.endpointB.setCoords()
}
// websocket
function moveEndpoint (id, positionX, positionY) {
  if (this.endpointA.id === id) {
    this.endpointA.set({ left: positionX, top: positionY, visible: true })
    this.set({
      x1: this.endpointA.left,
      y1: this.endpointA.top
    })
    this.setCoords()
  } else if (this.endpointB.id === id) {
    this.endpointB.set({ left: positionX, top: positionY, visible: true })
    this.set({
      x2: this.endpointB.left,
      y2: this.endpointB.top
    })
    this.setCoords()
  }
}

function connectLineEndpointToFigure (endpointId, connectedFigureId) {
  const connectedFigure = this.canvas.getFigure(connectedFigureId)
  if (this.endpointA.id === endpointId) {
    this.setEndpointWhenConnected(this.endpointA, connectedFigure)
  } else if (this.endpointB.id === endpointId) {
    this.setEndpointWhenConnected(this.endpointB, connectedFigure)
  }
}

function disconnectLineEndpoint (endpointId) {
  if (this.endpointA.id === endpointId) {
    this.setEndpointWhenDisconnected(this.endpointA)
  } else if (this.endpointB.id === endpointId) {
    this.setEndpointWhenDisconnected(this.endpointB)
  }
}

function handleFigureMoving () {
  const connectedFigure = this.connectedFigure
  const { mt, mr, mb, ml } = connectedFigure.oCoords
  let point = this.line.endpointA.getPointByOrigin('left', 'top')
  if (this.line.endpointA === this) {
    point = this.line.endpointB.getPointByOrigin('left', 'top')
  }
  const aCoord = getClosestACoord(point, Object.values([mt, mr, mb, ml]))
  this.set({ left: aCoord.x, top: aCoord.y })
  this.setCoords()
  this.fire('moving')
}

function getClosestACoord (point, oCoords) {
  let minDistance = Number.MAX_VALUE
  let aCoord = oCoords[0]
  oCoords.forEach((coord) => {
    const x = point.x - coord.x
    const y = point.y - coord.y
    const distance = Math.sqrt((x * x) + (y * y))
    if (minDistance > distance) {
      aCoord = coord
      minDistance = distance
    }
  })
  return aCoord
}

function setEndpointWhenConnected (endpoint, connectedFigure) {
  endpoint.connectedFigure = connectedFigure
  endpoint._handleFigureMoving = handleFigureMoving.bind(endpoint)
  endpoint.connectedFigure.on('moving', endpoint._handleFigureMoving)
  endpoint.connectedFigure.on('scaling', endpoint._handleFigureMoving)
  endpoint.connectedFigure.on('websocket moving', endpoint._handleFigureMoving)
  this.set({
    evented: false
  })
  endpoint._handleFigureMoving()
}

function setEndpointWhenDisconnected (endpoint) {
  endpoint.connectedFigure.off('moving', endpoint._handleFigureMoving)
  endpoint.connectedFigure.off('scaling', endpoint._handleFigureMoving)
  endpoint.connectedFigure.off('websocket moving', endpoint._handleFigureMoving)
  endpoint.connectedFigure = null
  endpoint._handleFigureMoving = null
  endpoint.connectedFigureId = ''
}
