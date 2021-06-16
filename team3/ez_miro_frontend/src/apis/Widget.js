import axios from 'axios'
import { host } from '../config/config'

export const CreateStickyNote = async (boardId, info) => {
  try {
    const res = await axios.post(`${host}/ez-miro/boards/${boardId}/widgets/sticky-notes/`, info)
    return res.data
  } catch (err) {
    console.error(err)
  }
}

export const ReadStickyNoteBy = async (id, boardId) => {
  try {
    const res = await axios.get(`${host}/ez-miro/boards/${boardId}/widgets/sticky-notes/${id}`)
    return res.data
  } catch (err) {
    console.error(err)
  }
}

export const DeleteStickyNoteBy = async (id, boardId) => {
  try {
    const res = await axios.delete(`${host}/ez-miro/boards/${boardId}/widgets/sticky-notes/${id}`)
    return res.data
  } catch (err) {
    console.error(err)
    return err
  }
}

export const DeleteLineBy = async (id, boardId) => {
  try {
    const res = await axios.delete(`${host}/ez-miro/boards/${boardId}/widgets/lines/${id}`)
    return res.data
  } catch (err) {
    console.error(err)
    return err
  }
}

export const EditTextOfStickyNoteBy = async (id, boardId, newText) => {
  try {
    const res = await axios.put(`${host}/ez-miro/boards/${boardId}/widgets/sticky-notes/${id}/text`, { newText })
    return res.data
  } catch (err) {
    console.log(err)
    return err
  }
}

export const MoveStickyNoteBy = async (boardId, widgets) => {
  try {
    const res = await axios.put(`${host}/ez-miro/boards/${boardId}/widgets/sticky-notes/move`, widgets)
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

export const ChangeColorOfStickyNoteBy = async (id, boardId, color) => {
  try {
    const res = await axios.put(`${host}/ez-miro/boards/${boardId}/widgets/sticky-notes/${id}/color`, { color })
    return res.data
  } catch (err) {
    console.log(err)
    return err
  }
}

export const ChangeZOrderOfStickyNoteBy = async (id, boardId, zOrder) => {
  try {
    const res = await axios.put(`${host}/ez-miro/boards/${boardId}/widgets/sticky-notes/${id}/z-order`, { zOrder })
    return res.data
  } catch (err) {
    console.log(err)
    return err
  }
}

export const EditFontSizeOfStickyNoteBy = async (id, boardId, fontSize) => {
  try {
    const res = await axios.put(`${host}/ez-miro/boards/${boardId}/widgets/sticky-notes/${id}/font-size`, { fontSize })
    return res.data
  } catch (err) {
    console.log(err)
    return err
  }
}

export const CreateLine = async (boardId, info) => {
  try {
    const res = await axios.post(`${host}/ez-miro/boards/${boardId}/widgets/lines/`, info)
    return res.data
  } catch (err) {
    console.error(err)
  }
}

export const MoveLineBy = async (boardId, widgets) => {
  try {
    const res = await axios.put(`${host}/ez-miro/boards/${boardId}/widgets/lines/move`, widgets)
    return res.data
  } catch (err) {
    console.log(err)
    return err
  }
}

export const LinkLine = async (boardId, info) => {
  try {
    const res = await axios.put(`${host}/ez-miro/boards/${boardId}/widgets/lines/link`, info)
    return res.data
  } catch (err) {
    console.error(err)
  }
}

export const DisconnectLine = async (boardId, info) => {
  try {
    const res = await axios.put(`${host}/ez-miro/boards/${boardId}/widgets/lines/disconnect`, info)
    return res.data
  } catch (err) {
    console.error(err)
  }
}
