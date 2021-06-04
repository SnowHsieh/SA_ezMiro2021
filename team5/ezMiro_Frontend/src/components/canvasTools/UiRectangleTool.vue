<template>
  <div class="ui-rectangle-tool component"></div>
</template>

<script>
import customEvents from "../../utils/customEvents";
import EventBus from "../../EventBus";
import { fabric } from "fabric";

export default {
  name: "UiRectangleTool",
  props: {
    canvasProp: null,
  },
  data() {
    return {
      canvas: null,
    };
  },
  mounted() {
    EventBus.on(customEvents.canvasTools.rectangle, (payload) => {
      this.canvas = this.canvasProp;
      this.canvas.isDrawingMode = false;
      this.createRectangle(payload);
    });
  },
  methods: {
    createRectangle(options) {
      const rect = new fabric.Rect({
        left: 100,
        top: 100,
        width: 150,
        height: 120,
        stroke: options.stroke,
        fill: options.fill,
        // whitebirdData: { id: uuid }
      });

      this.canvas.add(rect).setActiveObject(rect);
      this.canvas.renderAll();
    },
  },
};
</script>
