<template>
  <div class="ui-textbox-tool component"></div>
</template>

<script>
import customEvents from "../../utils/customEvents";
import EventBus from "../../EventBus";
import { fabric } from "fabric";

export default {
  name: "UiTextboxTool",
  props: {
    canvasProp: null,
  },
  data() {
    return {
      canvas: null,
    };
  },
  mounted() {
    EventBus.on(customEvents.canvasTools.textbox, (payload) => {
      this.canvas = this.canvasProp;
      this.canvas.isDrawingMode = false;
      this.createTextBox(payload);
    });
  },
  methods: {
    createTextBox() {
      const tbox = new fabric.Textbox("text box", {
        left: 100,
        top: 100,
        fill: "rgb(0,0,0)",
        fontFamily: "Arial",
        // whitebirdData: { id: uuid },
      });

      const invisibleControls = ["mt", "mr", "ml", "mb"];
      invisibleControls.forEach((side) => {
        tbox.setControlVisible(side, false);
      });

      this.canvas.add(tbox).setActiveObject(tbox);
      this.canvas.renderAll();
    },
  },
};
</script>
