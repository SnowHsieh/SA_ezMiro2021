var sendCursorTimerId = ''
const handleMessageEventMap = {}

export default function () {
  console.log(this)
  this.user = {
    name: `匿名使用者${Math.floor(Math.random() * 1000) + 1}`
  }

  this.webSocket = new WebSocket(
    `ws://localhost:8080/WebSocketServer/${this.boardId}/${this.user.name}`
  )

  this.webSocket.onopen = (e) => {
    console.log(e)
    console.log('WebSocket connected.')
  }

  registerMessageHandleEvent.call(this)

  this.webSocket.onmessage = async (e) => {
    const data = await JSON.parse(e.data)
    if (data.userId === this.user.name) {
      return
    }

    const isActive = this.canvas.getActiveObjects().some((target) => {
      return target.figureId === data.figureId
    })
    if (isActive) {
      return
    }
    if (handleMessageEventMap[data.type]) {
      handleMessageEventMap[data.type].call(this, data)
    }
  }

  this.webSocket.onclose = (e) => {
    console.log(e)
    clearInterval(sendCursorTimerId)
  }

  sendCursor.call(this)

  // this.webSocket.onmessage = async (e) => {
  //   const data = await JSON.parse(e.data)
  //   if (data.userId === this.user.name) {
  //     return
  //   }

  //   const isActive = this.canvas.getActiveObjects().some((target) => {
  //     console.log(target.isType('endpoint'))
  //     if (target.isType('endpoint')) {
  //       return target.line.figureId === data.figureId
  //     }
  //     return target.figureId === data.figureId
  //   })
  //   if (isActive) {
  //     return
  //   }

  //   if (data.type === 'BoardEntered') {
  //     this.activeCursorCanvas.addCursor(data.userId)
  //     this.webSocket.send(
  //       JSON.stringify({ x: this.mousePosition.x, y: this.mousePosition.y })
  //     )
  //   } else if (data.type === 'CursorMoved') {
  //     const userCursor = this.activeCursorCanvas.getCursor(data.userId)

  //     if (userCursor === undefined) {
  //       this.activeCursorCanvas.addCursor(data.userId)
  //     }

  //     this.activeCursorCanvas.moveCursor(
  //       data.userId,
  //       data.positionX,
  //       data.positionY
  //     )
  //   } else if (data.type === 'BoardLeft') {
  //     this.activeCursorCanvas.removeCursor(data.userId)
  //   } else if (data.type === 'NotePosted') {
  //     this.canvas.addNote(data.figureId, data.leftTopPositionX, data.leftTopPositionY, data.width, data.height, data.color, '')
  //   } else if (data.type === 'NoteMoved') {
  //     this.canvas.moveNote(data.figureId, data.newLeftTopPositionX, data.newLeftTopPositionY)
  //   } else if (data.type === 'NoteColorChanged') {
  //     // TODO
  //   } else if (data.type === 'NoteResized') {
  //     this.canvas.resizeNote(data.figureId, data.newWidth, data.newHeight)
  //   } else if (data.type === 'NoteTextEdited') {
  //     this.canvas.changeNoteText(data.figureId, data.newText)
  //   } else if (data.type === 'FigureDeleted') {
  //     this.canvas.removeFigure(data.figureId)
  //   } else if (data.type === 'LineDrew') {
  //     this.canvas.addLine(data.figureId, data.endpointA, data.endpointB)
  //   } else if (data.type === 'LineMoved') {
  //     console.log(data)
  //     this.canvas.moveLine(data.figureId, data.offsetX, data.offsetY)
  //   } else if (data.type === 'LineEndpointMoved') {
  //     this.canvas.moveLineEndpoint(data.figureId, data.endpointId, data.newPositionX, data.newPositionY)
  //   }
  //   this.canvas.renderAll()
  // }
}

function registerMessageHandleEvent () {
  handleMessageEventMap.BoardEntered = _handleBoardEntered
  handleMessageEventMap.CursorMoved = _handleCursorMoved
  handleMessageEventMap.BoardLeft = _handleBoardLeft
}

function sendCursor () {
  const preMousePosition = {
    x: 0,
    y: 0
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
  }, 250)
}

function _handleBoardEntered (data) {
  console.log('_handleBoardEnterd')
  this.activeCursorCanvas.addCursor(data.userId)
  this.webSocket.send(
    JSON.stringify({ x: this.mousePosition.x, y: this.mousePosition.y })
  )
}

function _handleCursorMoved (data) {
  const userCursor = this.activeCursorCanvas.getCursor(data.userId)

  if (userCursor === undefined) {
    this.activeCursorCanvas.addCursor(data.userId)
  }

  this.activeCursorCanvas.moveCursor(
    data.userId,
    data.positionX,
    data.positionY
  )
}

function _handleBoardLeft (data) {
  this.activeCursorCanvas.removeCursor(data.userId)
}
