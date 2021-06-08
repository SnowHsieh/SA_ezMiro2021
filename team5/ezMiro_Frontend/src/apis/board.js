import request from './https'

const board = {
  createBoard (params) {
    return request('post', `/projects/${params.projectId}/boards`, params)
  },
  getBoardContent (params) {
    return request('get', `/boards/${params.boardId}/content`, params)
  },
  bringFigureForward (params) {
    return request('post', '/bring-figure-forward', params)
  },
  bringFigureToFront (params) {
    return request('post', '/bring-figure-to-front', params)
  },
  sendFigureBackwards (params) {
    return request('post', '/send-figure-backwards', params)
  },
  sendFigureToBack (params) {
    return request('post', '/send-figure-to-back', params)
  }
}

export default board
