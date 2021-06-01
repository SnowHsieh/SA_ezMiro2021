import { fabric } from 'fabric'

fabric.OurLine = fabric.util.createClass(fabric.Line, {
  type: 'line',
  id: null,

  initialize: function (element, options) {
    options = options || {}
    this.id = element.id
    this._setControlVisible(this)
    this.callSuper('initialize', element.coors, options)
  },
  _setControlVisible (fabricObject) {
    fabricObject.setControlsVisibility({
      bl: false, // 左下
      br: false, // 右下
      mb: false, // 下中
      ml: false, // 中左
      mr: false, // 中右
      mt: false, // 上中
      tl: false, // 上左
      tr: false, // 上右
      mtr: false // 旋轉控制鍵
    })
  },
  _render (ctx) {
    // 先畫出原本的圖形
    this.callSuper('_render', ctx)
  }
})
