import request from './https'

const note = {
    postNote (params) {
        return request('post', `/notes?boardId=${params.boardId}`, params)
    }
}

export default note