import axios from 'axios'
import { host } from '../config/config'

export const CreateStickyNote = async (boardId, info) => {
  try {
    const res = await axios.post(`${host}/ez-miro/boards/${boardId}/widgets/sticky-notes/`, info)
    return res.data
  } catch (err) {
    console.log(err)
  }
}

export const ReadStickyNoteBy = async (id, boardId) => {
  try {
    const res = await axios.get(`${host}/ez-miro/boards/${boardId}/widgets/${id}`)
    console.log(res.data)
    return res.data
  } catch (err) {
    console.log(err)
  }
}
