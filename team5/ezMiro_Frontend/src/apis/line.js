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
    }
}

export default line