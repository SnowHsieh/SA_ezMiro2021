import request from './https'

const note = {
    postNote (params) {
        return request('post', '/post-note', params)
    },
    moveNote (params) {
        return request('post', '/move-note', params)
    },
    resizeNote (params) {
        return request('post', '/resize-note', params)
    },
    editNoteText (params) {
        return request('post', '/edit-note-text', params)
    },
    deleteNote (params) {
        return request('post', '/delete-note', params)
    },
    changeNoteColor (params) {
        return request('post', '/change-note-color', params)
    }
}

export default note