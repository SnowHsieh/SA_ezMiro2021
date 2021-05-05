import axios from 'axios'
import { host } from '../config/config'

export const GetBoardContent = async (boardId) => {
  try {
    const res = await axios.get(`${host}/ez-miro/boards/${boardId}/content`)
    return res.data
  } catch (err) {
    console.log(err)
  }
}

export const CreateBoard = async () => {
  try {
    const res = await axios.post(`${host}/ez-miro/boards/`)
    return res.data
  } catch (err) {
    console.log(err)
  }
}

export const DeleteStickyNoteBy = async (id, boardId) => {
  try {
    const res = await axios.post(`${host}/ez-miro/boards/${boardId}/widgets/${id}`)
    return res.data
  } catch (err) {
    console.log(err)
  }
}
