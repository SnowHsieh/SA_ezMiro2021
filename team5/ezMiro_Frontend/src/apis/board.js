import request from './https'

const board = {
    createBoard (params) {
        return request('post', `/projects/${params.projectId}/boards`, params)
    },
    getBoardContent (params) {
        return request('get', `/boards/${params.boardId}/content`, params)
    },
    bringFigureToFront (params) {
        return request('post', `/bring-figure-front`, params)
    },
    bringFigureToFrontEnd (params) {
        return request('post', `/bring-figure-front-end`, params)
    },
    sendFigureToBack (params) {
        return request('post', `/send-figure-back`, params)
    },
    sendFigureToBackEnd (params) {
        return request('post', `/send-figure-back-end`, params)
    }
}

export default board