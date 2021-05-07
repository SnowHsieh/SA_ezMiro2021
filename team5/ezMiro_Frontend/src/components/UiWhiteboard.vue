<template>
  <div class="ui-whiteboard component" :class="backgroundImage">
    <canvas id="canvas"></canvas>
    <UiDrawingTool :canvasProp="canvas" />
    <UiRectangleTool :canvasProp="canvas" />
    <UiCircleTool :canvasProp="canvas" />
    <UiTextboxTool :canvasProp="canvas" />
    <UiStickyNoteTool :canvasProp="canvas" />
  </div>
</template>

<script>
import { markRaw } from "@vue/reactivity";
import { fabric } from "fabric";
import UiDrawingTool from "@/components/canvasTools/UiDrawingTool";
import UiRectangleTool from "@/components/canvasTools/UiRectangleTool";
import UiCircleTool from "@/components/canvasTools/UiCircleTool";
import UiTextboxTool from "@/components/canvasTools/UiTextboxTool";
import UiStickyNoteTool from "@/components/canvasTools/UiStickyNoteTool";
import customEvents from "../utils/customEvents";
import EventBus from "../EventBus";

export default {
  name: "UiWhiteboard",
  components: {
    UiDrawingTool,
    UiRectangleTool,
    UiCircleTool,
    UiTextboxTool,
    UiStickyNoteTool,
  },
  data() {
    return {
      canvas: null,
      backgroundImage: "dots",
    };
  },
  mounted() {
    this.canvas = markRaw(
      new fabric.Canvas("canvas", {
        width: 1247,
        height: 1100
      })
    );

    EventBus.on(customEvents.canvas.imageBackgroundChanged, (payload) => {
      this.backgroundImage = payload;
    });
  },
};
</script>

<style scoped>
@import "../assets/scss/_whiteboard.scss";
</style>
