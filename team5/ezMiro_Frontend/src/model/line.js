import { fabric } from 'fabric'

function initialize (line, options) {
    console.log(line)
    this.id = line.id
    this.endpointA = createEndpoint(line.endpointA, this.id)
    initializeEnpoint(this.endpointA)
    this.endpointB = createEndpoint(line.endpointB, this.id)
    initializeEnpoint(this.endpointB)
    setControlsVisibility(this)
    const positions = [line.endpointA.positionX, line.endpointA.positionY, line.endpointB.positionX, line.endpointB.positionY]
    this.callSuper('initialize', positions, options)
    const thisLine = this
    this.endpointA.on('moving', function (e) {
        thisLine.set({ x1: e.pointer.x, y1: e.pointer.y })
    })
    this.endpointB.on('moving', function (e) {
        thisLine.set({ x2: e.pointer.x, y2: e.pointer.y })
    })
    // this.on('moving', () => {
    //     console.log(thisLine)
    //     thisLine.endpointA.set( {
    //         left: thisLine.left,
    //         top: thisLine.top
    //     })
    //     thisLine.endpointB.set( {
    //         left: thisLine.left + thisLine.width,
    //         top: thisLine.top + thisLine.height
    //     })
    // })
}

function createEndpoint (endpoint, figureId) {
    return new fabric.Circle({
        figureId: figureId,
        figureType: 'LINE',
        endpointId: endpoint.endpointId,
        left: endpoint.positionX,
        top: endpoint.positionY,
        strokeWidth: 5,
        radius: 8,
        fill: '#ffffff',
        stroke: '#666'
    })
}

function initializeEnpoint (endpoint) {
    endpoint.hasControls = false 
    endpoint.hasBorders = false
}

function setControlsVisibility (line) {

    line.hasControls = false 
    line.hasBorders = false
    // line.setControlsVisibility({
    //     bl: false,
    //     br: false,
    //     mb: false,
    //     ml: false,
    //     mr: false,
    //     mt: false,
    //     tl: false,
    //     tr: false,
    //     mtr: false
    // })
}

fabric.OurLine = fabric.util.createClass(fabric.Line, {
    id: null,
    endpointA: null,
    ednpointB: null,
    initialize: initialize
})
