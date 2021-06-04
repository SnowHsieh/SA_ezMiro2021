import { fabric } from "fabric";

export default fabric.util.createClass(fabric.Line, {
  figureId: null,
  figureType: null,
  endpointA: null,
  ednpointB: null,
  initialize: initialize,
});

function initialize(line, options) {
  this.figureId = line.id;
  this.figureType = "LINE";
  this.endpointA = createEndpoint(this, line.endpointA, this.figureId);
  initializeEnpoint(this.endpointA);
  this.endpointB = createEndpoint(this, line.endpointB, this.figureId);
  initializeEnpoint(this.endpointB);
  setControlsVisibility(this);
  const positions = [
    line.endpointA.positionX,
    line.endpointA.positionY,
    line.endpointB.positionX,
    line.endpointB.positionY,
  ];
  this.callSuper("initialize", positions, options);
  const thisLine = this;
  this.endpointA.on("moving", function (e) {
    thisLine.set({ x1: e.pointer.x, y1: e.pointer.y });
  });
  this.endpointB.on("moving", function (e) {
    thisLine.set({ x2: e.pointer.x, y2: e.pointer.y });
  });
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

function createEndpoint(line, endpoint, figureId) {
  return new fabric.Circle({
    line: line,
    figureId: figureId,
    figureType: "LINE_ENDPOINT",
    endpointId: endpoint.endpointId,
    left: endpoint.positionX,
    top: endpoint.positionY,
    strokeWidth: 5,
    radius: 8,
    fill: "#ffffff",
    stroke: "#666",
  });
}

function initializeEnpoint(endpoint) {
  endpoint.hasControls = false;
  endpoint.hasBorders = false;
}

function setControlsVisibility(line) {
  line.hasControls = false;
  line.hasBorders = false;
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
