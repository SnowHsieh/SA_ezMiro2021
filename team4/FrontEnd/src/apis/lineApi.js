import axios from 'axios'
import { hostIp } from '../config/config.js'

export const createLineApi = async (boardId) => {
  try {
    const res = await axios.post(`${hostIp}/board/${boardId}/createLine`,
      {
        positionList: [
          {
            x: 100.0,
            y: 100.0
          }, {
            x: 200.0,
            y: 200.0
          }, {
            x: 300.0,
            y: 300.0
          }
        ],
        strokeWidth: 5,
        color: '#000000'
      }
    )
    return res
  } catch (err) {
    console.log(err)
  }
}

export const deleteLine = async (boardId, figure) => {
  try {
    const res = await axios.post(`${hostIp}/board/${boardId}/deleteLine`,
      {
        figureId: figure.get('id')
      }
    )
    return res
  } catch (err) {
    console.log(err)
  }
}

export const changeLinePath = async (boardId, figure) => {
  try {
    const newPositionList = figure.points
    const res = await axios.post(`${hostIp}/board/${boardId}/changeLinePath`,
      {
        figureId: figure.get('id'),
        positionList: newPositionList
      }
    )
    return res
  } catch (err) {
    console.log(err)
  }
}

export const attachTextfigure = async (boardId, lineId, textFigureId) => {
  try {
    const res = await axios.post(`${hostIp}/board/${boardId}/attachTextfigure`,
      {
        figureId: lineId,
        textFigureId: textFigureId
      }
    )
    return res
  } catch (err) {
    console.log(err)
  }
}
