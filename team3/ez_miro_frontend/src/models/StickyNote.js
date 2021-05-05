import { fabric } from 'fabric'

fabric.StickyNote = fabric.util.createClass(fabric.Rect, {
  type: 'stickynote',
  id: null,
  text: 'owo',
  initialize: function (element, options) {
    options = options || {}
    this.callSuper('initialize', element, options)
    this.id = element.id
    this.setControlsVisibility({
      bl: true, // 左下
      br: true, // 右下
      mb: false, // 下中
      ml: false, // 中左
      mr: false, // 中右
      mt: false, // 上中
      tl: true, // 上左
      tr: true, // 上右
      mtr: true // 旋轉控制鍵
    })
    this.text = element.text
  }
})

fabric.StickyNoteNew = fabric.util.createClass(fabric.Group, {
  type: 'stickynote',
  id: null,
  textObject: null,
  rectObject: null,
  initialize: function (element, options) {
    options = options || {}
    this.callSuper('initialize', element, options)
    this.id = element.id
    this.rectObject = this._initailizeRect(element)
    this.textObject = this._initailizeText(element)
    this._setRectControlsVisibility(this.rectObject)
    this._objects = [this.rectObject, this.textObject]
  },
  _initailizeRect (element) {
    return new fabric.Rect({
      id: element.id,
      left: element.left,
      top: element.top,
      height: element.height,
      width: element.width,
      fill: element.fill
    })
  },
  _initailizeText (element) {
    return new fabric.IText(element.text, {
      left: element.left,
      top: element.top,
      fill: element.textColor
    })
  },
  _setRectControlsVisibility (rect) {
    rect.setControlsVisibility({
      bl: true, // 左下
      br: true, // 右下
      mb: false, // 下中
      ml: false, // 中左
      mr: false, // 中右
      mt: false, // 上中
      tl: true, // 上左
      tr: true, // 上右
      mtr: true // 旋轉控制鍵
    })
  }
})
