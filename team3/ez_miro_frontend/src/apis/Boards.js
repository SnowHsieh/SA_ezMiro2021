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
