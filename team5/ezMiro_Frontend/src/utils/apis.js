import api from '@/apis'

const board = {
  async createBoard (projectId, name) {
    var boardId = null
    await api.board.createBoard({
      projectId: projectId,
      name: name
    }).then((response) => {
      boardId = response.data.id
    })
    return boardId
  },
  async getBoardContent (boardId) {
    var figures = []
    await api.board.getBoardContent({
      boardId: boardId
    }).then((response) => {
      figures = response.data.figureDtos
    })
    return figures
  },
  bringFigureForward (boardId, figureId) {
    api.board.bringFigureForward({
      boardId: boardId,
      figureId: figureId
    })
  },
  bringFigureToFront (boardId, figureId) {
    api.board.bringFigureToFront({
      boardId: boardId,
      figureId: figureId
    })
  },
  sendFigureBackwards (boardId, figureId) {
    api.board.sendFigureBackwards({
      boardId: boardId,
      figureId: figureId
    })
  },
  sendFigureToBack (boardId, figureId) {
    api.board.sendFigureToBack({
      boardId: boardId,
      figureId: figureId
    })
  }
}

const note = {
  async postNote (boardId, leftTopPositionX, leftTopPositionY, width, height, color) {
    var id = null
    await api.note.postNote({
      boardId: boardId,
      leftTopPositionX: leftTopPositionX,
      leftTopPositionY: leftTopPositionY,
      height: height,
      width: width,
      color: color
    }).then((response) => {
      id = response.data.id
    })
    return id
  },
  moveNote (figureId, leftTopPositionX, leftTopPositionY) {
    api.note.moveNote({
      figureId: figureId,
      leftTopPositionX: leftTopPositionX,
      leftTopPositionY: leftTopPositionY
    }).then()
  },
  resizeNote (figureId, width, height) {
    api.note.resizeNote({
      figureId: figureId,
      height: height,
      width: width
    }).then()
  },
  editNoteText (figureId, text) {
    api.note.editNoteText({
      figureId: figureId,
      text: text
    }).then()
  },
  deleteNote (figureId) {
    api.note.deleteNote({
      figureId: figureId
    }).then()
  },
  changeNoteColor (figureId, color) {
    api.note.changeNoteColor({
      figureId: figureId,
      color: color
    }).then()
  }
}

const line = {
  async drawLine (boardId, endpointA, endpointB) {
    var id = null
    await api.line.drawLine({
      boardId: boardId,
      endpointA: endpointA,
      endpointB: endpointB
    }).then((response) => {
      id = response.data.id
    })
    return id
  },
  moveLine (figureId, offsetX, offsetY) {
    api.line.moveLine({
      figureId: figureId,
      offsetX: offsetX,
      offsetY: offsetY
    }).then()
  },
  moveLineEndpoint (figureId, endpointId, positionX, positionY) {
    api.line.moveLineEndpoint({
      figureId: figureId,
      endpointId: endpointId,
      positionX: positionX,
      positionY: positionY
    }).then()
  },
  deleteLine (figureId) {
    api.line.deleteLine({
      figureId: figureId
    }).then()
  },
  connectLine (figureId, endpointId, connectFigureId) {
    api.line.connectLine({
      figureId: figureId,
      endpointId: endpointId,
      connectFigureId: connectFigureId
    }).then()
  },
  disconnectLine (figureId, endpointId) {
    api.line.disconnectLine({
      figureId: figureId,
      endpointId: endpointId
    }).then()
  }
}

export { board, note, line }
export default { board, note, line }
