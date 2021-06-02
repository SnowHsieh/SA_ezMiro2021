import { fabric } from 'fabric'
const fontSize = 18

fabric.StickyNote = fabric.util.createClass(fabric.Group, {
  type: 'stickyNote',
  id: null,
  textObject: null,
  rectObject: null,
  initialize: function (element, options) {
    options = options || {}
    this.id = element.id
    this.rectObject = this._initailizeRect(element)
    this.textObject = this._initailizeText(element)
    this._setTextOnInput(this.textObject, element)
    this._objects = [this.rectObject, this.textObject]
    this.callSuper('initialize', this._objects, {
      subTargetCheck: true,
      scaleX: element.width / 100,
      scaleY: element.height / 100
    })
    this._setControlVisible(this)
  },
  _initailizeRect (element) {
    return new fabric.Rect({
      left: element.left,
      top: element.top,
      height: 100,
      width: 100,
      fill: element.fill
    })
  },
  _initailizeText (element) {
    return new fabric.Textbox(element.text, {
      left: element.left + 0.5 * 100,
      top: element.top + 0.5 * 100,
      fill: element.textColor,
      fontSize: element.fontSize || fontSize,
      width: 100,
      maxWidth: 100,
      textAlign: 'center',
      originX: 'center',
      originY: 'center',
      splitByGrapheme: true
    })
  },
  _setControlVisible (fabricObject) {
    fabricObject.setControlsVisibility({
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
