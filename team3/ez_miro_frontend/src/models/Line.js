import { fabric } from 'fabric'

fabric.OurLine = fabric.util.createClass(fabric.Line, {
  type: 'line',
  id: null,
  circleHead: null,
  circleTail: null,
  initialize: function (element, options) {
    options = options || {}
    this.id = element.id
    this._setControlVisible(this)
    this._initailizeCircle(element.coors)
    const coors = [element.coors.topLeftX, element.coors.topLeftY, element.coors.bottomRightX, element.coors.bottomRightY]
    this.callSuper('initialize', coors, options)
    const thisLine = this
    this.circleHead.on('moving', function (e) {
      thisLine.set({ x1: e.pointer.x, y1: e.pointer.y })
    })
    this.circleTail.on('moving', function (e) {
      thisLine.set({ x2: e.pointer.x, y2: e.pointer.y })
    })
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
  _initailizeCircle (coors) {
    this.circleHead = new fabric.Circle({
      left: coors.topLeftX,
      top: coors.topLeftY,
      strokeWidth: 5,
      radius: 12,
      fill: '#fff',
      stroke: '#666'
    })
    this.circleHead.hasControls = this.circleHead.hasBorders = false
    this.circleTail = new fabric.Circle({
      left: coors.bottomRightX,
      top: coors.bottomRightY,
      strokeWidth: 5,
      radius: 12,
      fill: '#fff',
      stroke: '#666'
    })
    this.circleTail.hasControls = this.circleTail.hasBorders = false
  },

  _render (ctx) {
    // 先畫出原本的圖形
    this.callSuper('_render', ctx)
  }
})
