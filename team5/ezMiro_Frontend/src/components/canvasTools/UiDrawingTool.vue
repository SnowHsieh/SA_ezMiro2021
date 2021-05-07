<template>
  <div class="ui-drawing-tool component"></div>
</template>

<script>
import customEvents from "../../utils/customEvents";
import EventBus from "../../EventBus";

export default {
  name: "UiDrawingTool",
  props: {
    canvasProp: null,
  },
  data() {
    return {
      canvas: null,
    };
  },
  mounted() {
    EventBus.on(customEvents.canvasTools.drawing, (payload) => {
      this.canvas = this.canvasProp;
      this.canvas.isDrawingMode = payload.drawingMode;

      this.canvas.on('mouse:up', () => {
        if (this.canvas.isDrawingMode) {
          const canvasObjectCount = this.canvas.getObjects().length;
          if (this.canvas.getObjects()[canvasObjectCount - 1].type === 'path') {
            const PathObject = this.canvas.getObjects()[canvasObjectCount - 1];
            this.canvas.remove(this.canvas.getObjects()[canvasObjectCount - 1]);
            // PathObject.whitebirdData = { id: uuid };
            this.canvas.add(PathObject);
          }
        }
      })
    });
  },
};
</script>
