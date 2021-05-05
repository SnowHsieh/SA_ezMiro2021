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
    const res = await axios.get(`${host}/ez-miro/boards/${boardId}/widgets/sticky-notes/${id}`)
    console.log(res.data)
    return res.data
  } catch (err) {
    console.log(err)
  }
}

export const DeleteStickyNoteBy = async (id, boardId) => {
  try {
    const res = await axios.delete(`${host}/ez-miro/boards/${boardId}/widgets/sticky-notes/${id}`)
    return res.data
  } catch (err) {
    console.log(err)
    return err
  }
}

export const EditTextOfStickyNoteBy = async (id, boardId, newText) => {
  try {
    const res = await axios.put(`${host}/ez-miro/boards/${boardId}/widgets/sticky-notes/${id}/edit-text`, { newText })
    return res.data
  } catch (err) {
    console.log(err)
    return err
  }
}

export const MoveStickyNoteBy = async (id, boardId, widgets) => {
  try {
    const res = await axios.put(`${host}/ez-miro/boards/${boardId}/widgets/sticky-notes/${id}/move`, widgets)
    return res.data
  } catch (err) {
    console.log(err)
    return err
  }
}

export const ResizeStickyNoteBy = async (id, boardId, coordinate) => {
  try {
    const res = await axios.put(`${host}/ez-miro/boards/${boardId}/widgets/sticky-notes/${id}/resize`, {
      topLeftX: coordinate.topLeftX,
      topLeftY: coordinate.topLeftY,
      bottomRightX: coordinate.bottomRightX,
      bottomRightY: coordinate.bottomRightY
    })
    return res.data
  } catch (err) {
    console.log(err)
    return err
  }
}
