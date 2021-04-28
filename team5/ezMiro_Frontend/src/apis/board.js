import request from './https'

const board = {
    createBoard(params) {
        return request('post', `/projects/${params.projectId}/boards`, params)
    },
    getBoardContent(params) {
        return request('get', `/boards/${params.boardId}/content`, params)
    }
}

export default board