import axios from 'axios'
import { hostIp } from '../config/config.js'

export const createStickyNoteApi = async (boardId) => {
  try {
    const res = await axios.post(`${hostIp}/board/${boardId}/createStickyNote`,
      {
        content: '',
        position: {
          x: 100,
          y: 100
        },
        style: {
          fontSize: 20,
          shape: 2,
          width: 150.0,
          height: 150.0,
          color: '#ffa150'
        }
      }
    )
    return res
  } catch (err) {
    console.log(err)
  }
}

export const changeStickyNoteContentApi = async (boardId, figure) => {
  try {
    const res = await axios.post(`${hostIp}/board/${boardId}/changeStickyNoteContent`,
      {
        figureId: figure.get('id'),
        content: figure.item(1).get('text')
      }
    )
    return res
  } catch (err) {
    console.log(err)
  }
}

export const changeStickyNoteColorApi = async (boardId, figure) => {
  try {
    const res = await axios.post(`${hostIp}/board/${boardId}/changeStickyNoteColor`,
      {
        figureId: figure.get('id'),
        color: figure.item(0).get('fill')
      }
    )
    return res
  } catch (err) {
    console.log(err)
  }
}

export const resizeStickyNoteApi = async (boardId, figure) => {
  try {
    const res1 = await axios.post(`${hostIp}/board/${boardId}/resizeStickyNote`,
      {
        figureId: figure.get('id'),
        width: parseFloat(figure.width) * parseFloat(figure.get('scaleX')),
        height: parseFloat(figure.height) * parseFloat(figure.get('scaleY'))
      }
    )
    await axios.post(`${hostIp}/board/${boardId}/moveStickyNote`,
      {
        figureId: figure.get('id'),
        top: figure.get('top'),
        left: figure.get('left')
      }
    )
    // todo : Need to check if the time received DomainEvent may cause position changed?
    return res1
  } catch (err) {
    console.log(err)
  }
}

export const moveStickyNoteApi = async (boardId, figure) => {
  try {
    const res = await axios.post(`${hostIp}/board/${boardId}/moveStickyNote`,
      {
        figureId: figure.get('id'),
        top: figure.get('top'),
        left: figure.get('left')
      }
    )
    return res
  } catch (err) {
    console.log(err)
  }
}

export const deleteStickyNoteApi = async (boardId, figure) => {
  try {
    const res = await axios.post(`${hostIp}/board/${boardId}/deleteStickyNote`,
      {
        figureId: figure.get('id')
      }
    )
    return res
  } catch (err) {
    console.log(err)
  }
}
