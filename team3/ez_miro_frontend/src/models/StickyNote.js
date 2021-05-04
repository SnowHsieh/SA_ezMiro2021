import { fabric } from 'fabric'

fabric.StickyNote = fabric.util.createClass(fabric.Rect, {
  type: 'stickynote',
  id: null,
  initialize: function (element, options) {
    options = options || {}
    this.callSuper('initialize', element, options)
    this.id = element.id
  }
})
