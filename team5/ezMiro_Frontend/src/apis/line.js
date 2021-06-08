import request from './https'

const line = {
  drawLine (params) {
    return request('post', '/draw-line', params)
  },
  moveLine (params) {
    return request('post', '/move-line', params)
  },
  moveLineEndpoint (params) {
    return request('post', '/move-line-endpoint', params)
  },
  deleteLine (params) {
    return request('post', '/delete-line', params)
  },
  connectLine (params) {
    return request('post', '/connect-line', params)
  },
  disconnectLine (params) {
    return request('post', '/disconnect-line', params)
  }
}

export default line
