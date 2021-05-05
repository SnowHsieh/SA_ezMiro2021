import axios from 'axios'
import { host } from '../config/config'

export const CreateStickyNote = async (boardId, info) => {
  try {
    const res = await axios.post(`${host}/ez-miro/boards/${boardId}/widgets/sticky-notes/`, info)
    return res.data
  } catch (err) {
    console.error(err)
  }
}

export const ReadStickyNoteBy = async (id, boardId) => {
  try {
    const res = await axios.get(`${host}/ez-miro/boards/${boardId}/widgets/sticky-notes/${id}`)
    return res.data
  } catch (err) {
    console.error(err)
  }
}

export const DeleteStickyNoteBy = async (id, boardId) => {
  try {
    const res = await axios.delete(`${host}/ez-miro/boards/${boardId}/widgets/sticky-notes/${id}`)
    return res.data
  } catch (err) {
    console.error(err)
    return err
  }
}
