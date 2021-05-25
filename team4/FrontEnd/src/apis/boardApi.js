import axios from 'axios'
import { hostIp } from '../config/config.js'

export const getBoardContentApi = async (boardId) => {
  try {
    const res = await axios.get(`${hostIp}/boards/${boardId}/content`)
    return res
  } catch (err) {
    console.log(err)
  }
}

export const changeFigureOrderApi = async (boardId, figureList) => {
  try {
    const res = await axios.post(`${hostIp}/boards/${boardId}/changeFigureOrder`,
      {
        figureOrderList: figureList
      }
    )
    return res
  } catch (err) {
    console.log(err)
  }
}
