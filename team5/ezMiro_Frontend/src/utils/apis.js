import api from '../apis'

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
    }
}

const note = {
    async postNote (boardId, leftTopPositionX, leftTopPositionY, height, width, color) {
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
        }).then((response) => {
            console.log(response.data)
        })
    },
    resizeNote (figureId, height, width) {
        api.note.resizeNote({
            figureId: figureId,
            height: height,
            width: width
        }).then((response) => {
            console.log(response.data)
        })
    },
    editNoteText (figureId, text) {
        api.note.editNoteText({
            figureId: figureId,
            text: text
        }).then((response) => {
            console.log(response.data)
        })
    },
    deleteNote (figureId) {
        api.note.deleteNote({
            figureId: figureId
        }).then((response) => {
            console.log(response.data)
        })
    },
    changeNoteColor (figureId, color) {
        api.note.changeNoteColor({
            figureId: figureId,
            color: color
        }).then((response) => {
            console.log(response.data)
        })
    }
}

export default {board, note}