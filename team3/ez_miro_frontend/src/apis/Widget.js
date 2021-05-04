import axios from 'axios'
import { host } from '../config/config'

export const CreateStickyNote = async (boardId) => {
  try {
    const res = await axios.post(`${host}/ez-miro/board/${boardId}/stickyNotes`)
    return res.data
  } catch (err) {
    console.log(err)
  }
}