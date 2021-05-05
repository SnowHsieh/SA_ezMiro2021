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
