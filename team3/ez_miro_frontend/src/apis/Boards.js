import axios from 'axios'
import { host } from '@/config/config'

export const EnterBoard = async (boardId, userId) => {
  try {
    const res = await axios.post(`${host}/ez-miro/boards/${boardId}`, { userId })
    return res.data
  } catch (err) {
    console.log(err)
  }
}

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
