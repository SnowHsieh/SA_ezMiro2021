import axios from 'axios'
import { hostIp } from '../config/config.js'

export const createLineApi = async (boardId, figure) => {
  try {
    const srcPoint = {
      x: figure.get('x1'),
      y: figure.get('y1')
    }
    const destPoint = {
      x: figure.get('x2'),
      y: figure.get('y2')
    }
    const newPositionList = []
    newPositionList.push(srcPoint)
    newPositionList.push(destPoint)
    const res = await axios.post(`${hostIp}/board/${boardId}/createLine`,
      {
        positionList: newPositionList,
        strokeWidth: figure.get('strokeWidth'),
        color: figure.get('stroke')
      }
    )
    return res
  } catch (err) {
    console.log(err)
  }
}

export const deleteLineApi = async (boardId, figure) => {
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

export const changeLinePathApi = async (boardId, figure) => {
  try {
    // console.log(figure)
    const srcPoint = {
      x: figure.get('x1'),
      y: figure.get('y1')
    }
    const destPoint = {
      x: figure.get('x2'),
      y: figure.get('y2')
    }
    const newPositionList = []
    newPositionList.push(srcPoint)
    newPositionList.push(destPoint)

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
