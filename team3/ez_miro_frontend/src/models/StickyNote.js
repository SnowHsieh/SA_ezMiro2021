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
      mtr: false // 旋轉控制鍵
    })
    this.text = element.text
  }
})

fabric.StickyNoteNew = fabric.util.createClass(fabric.Group, {
  type: 'stickynotenew',
  id: null,
  textObject: null,
  rectObject: null,
  initialize: function (element, options) {
    options = options || {}
    this.id = element.id
    this.rectObject = this._initailizeRect(element)
    this.textObject = this._initailizeText(element)
    this._setRectControlsVisibility(this.rectObject)
    this._setTextOnInput(this.textObject, element)
    this._objects = [this.rectObject, this.textObject]
    this.callSuper('initialize', this._objects, {
      subTargetCheck: true
    })
    this.on('mousedblclick', function (e) {
      // e.target.textObject.enterEditing()
      // e.target.textObject.selectAll()
    })
  },
  _initailizeRect (element) {
    return new fabric.Rect({
      left: element.left,
      top: element.top,
      height: element.height,
      width: element.width,
      fill: element.fill
    })
  },
  _initailizeText (element) {
    return new fabric.Textbox(element.text, {
      left: element.left + 0.5 * element.width,
      top: element.top + 0.5 * element.height,
      fill: element.textColor,
      fontSize: 36,
      width: element.width,
      maxWidth: element.width,
      textAlign: 'center',
      originX: 'center',
      originY: 'center',
      splitByGrapheme: true
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
  },
  _ungroup () {
    // this._objects._restoreObjectsState()
    console.log(this.canvas)
  },
  _setTextOnInput (iText, element) {
    var isLastInputDeletedOnce = false
    iText.onInput = function (e) {
      if (iText.height > element.height) {
        if (!isLastInputDeletedOnce) {
          iText.text = iText.text.slice(0, -1)
          isLastInputDeletedOnce = true
        }
        return
      }
      this.callSuper('onInput', e)
    }
  }
})
