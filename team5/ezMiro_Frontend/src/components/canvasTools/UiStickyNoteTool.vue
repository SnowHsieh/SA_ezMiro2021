<template>
  <div class="ui-sticky-note-tool component"></div>
</template>

<script>
import customEvents from "../../utils/customEvents";
import EventBus from "../../EventBus";
import { fabric } from "fabric";

import stickynoteBlack from "../../assets/images/stickynote/black.svg";
import stickynoteRed from "../../assets/images/stickynote/red.svg";
import stickynoteBlue from "../../assets/images/stickynote/blue.svg";
import stickynoteYellow from "../../assets/images/stickynote/yellow.svg";
import stickynoteGreen from "../../assets/images/stickynote/green.svg";
import stickynoteWhite from "../../assets/images/stickynote/white.svg";

export default {
  name: "UiSickyNoteTool",
  props: {
    canvasProp: null,
  },
  data() {
    return {
      canvas: null,
      editingText: false,
      groupObject: null,
      textBox: null,
      overText: false,
      textBoxChange: false,
    };
  },
  mounted() {
    EventBus.on(customEvents.canvasTools.stickyNote, (payload) => {
      this.canvas = this.canvasProp;
      this.canvas.on("mouse:down", () => {
        if (this.editingText === true) {
          this.leaveEditingMode();
        }
      });
      this.canvas.isDrawingMode = false;
      this.createStickyNote(payload);
    });
  },
  methods: {
    leaveEditingMode() {
      if (this.editingText === true && this.overText === false) {
        this.textBox.exitEditing();

        this.canvas.remove(this.textBox);

        this.groupObject.addWithUpdate(this.textBox);

        this.canvas.setActiveObject(this.groupObject);

        this.editingText = false;
        this.groupObject.set({
          selectable: true,
          evented: true,
        });

        this.resetData();
        this.canvas.renderAll();
      }
    },
    addStickyNoteSettings(group) {
      const invisibleControls = ["mt", "mr", "ml", "mb", "mtr"];
      invisibleControls.forEach((side) => {
        group.setControlVisible(side, false);
      });

      group.on("mousedblclick", () => {
        group.set({
          selectable: false,
          evented: false,
        });
        this.editingText = true;
        this.groupObject = group;

        this.textBox = group.item(1);
        this.textBox.scaleX *= group.scaleX;
        this.textBox.scaleY *= group.scaleY;
        this.textBox.left = group.left + 10 * this.textBox.scaleX;
        this.textBox.top = group.top + 10 * this.textBox.scaleY;

        group.remove(group.item(1));
        this.canvas.add(this.textBox).setActiveObject(this.textBox);
        this.textBox.enterEditing();

        this.canvas.renderAll();
      });
    },
    fontResizeStickyNote(textBox, group) {
      let lineNumber = 0;
      let maxLineTextWidth = 0;

      textBox._textLines.forEach(() => {
        const lineTextWidth = textBox.getLineWidth(lineNumber);
        if (lineTextWidth > maxLineTextWidth) {
          maxLineTextWidth = lineTextWidth;
        }
        lineNumber += 1;
      });
      textBox.width = maxLineTextWidth;

      const maxFixedWidth = group.item(0).width - 20;
      const maxFiexdHeight = group.item(0).height - 20;
      const maxFontSize = group.item(0).height - 20;

      let newFontSize = textBox.fontSize;

      newFontSize *= maxFixedWidth / (textBox.width + 1);
      if (newFontSize > maxFontSize) {
        newFontSize = maxFontSize;
        textBox.set({ fontSize: maxFontSize });
      } else {
        textBox.set({ fontSize: newFontSize });
      }
      textBox.width = maxFixedWidth;

      while (textBox.height > maxFiexdHeight) {
        const scale = textBox.height / maxFiexdHeight;
        if (textBox.fontSize > maxFontSize) {
          textBox.fontSize = maxFontSize;
        }
        if (scale >= 4) {
          newFontSize -= scale;
        } else if (scale < 4 && scale >= 1) {
          newFontSize -= 4;
        } else {
          newFontSize -= 1;
        }

        textBox.set({ fontSize: newFontSize });
      }
      this.canvas.renderAll();
    },
    addTextBoxSettings(textBox, group) {
      textBox.on("mouseover", () => {
        console.log("mouseover");
        this.overText = true;
      });
      textBox.on("mouseout", () => {
        console.log("mouseout");
        this.overText = false;
      });
      textBox.on("changed", () => {
        console.log("changed");
        this.textBoxChange = true;
        this.fontResizeStickyNote(textBox, group);
      });

      textBox.set({
        hasControls: false,
        hasBorders: false,
        lockMovementX: true,
        lockMovementY: true,
      });
    },
    resetData() {
      this.groupObject = null;
      this.textBox = null;
      this.textBoxChange = false;
    },
    getStickyNote(color) {
      let stickynote = "";
      switch (String(color)) {
        case "#000000":
          stickynote = stickynoteBlack;
          break;
        case "#8A0000":
          stickynote = stickynoteRed;
          break;
        case "#8CB8DE":
          stickynote = stickynoteBlue;
          break;
        case "#FFD54F":
          stickynote = stickynoteYellow;
          break;
        case "#58CA68":
          stickynote = stickynoteGreen;
          break;
        case "#FFFFFF":
          stickynote = stickynoteWhite;
          break;
        default:
          stickynote = stickynoteBlack;
          break;
      }
      return stickynote;
    },
    createStickyNote(payload) {
      fabric.loadSVGFromURL(
        this.getStickyNote(payload.color),
        (objects, options) => {
          const SVGObject = fabric.util.groupSVGElements(objects, options);
          /*SVGObject.whitebirdData = {
            id: uuid,
            type: "NOTE",
          };*/

          const text = new fabric.Textbox("", {
            left: 100,
            top: 100,
            width: SVGObject.width - 20,
            fontSize: 166.6,
            lockScalingY: true,
            fill: "rgb(0,0,0)",
            fontFamily: "Arial",
            // whitebirdData: { id: uuid }
          });

          if (payload.color === "#000000") {
            text.set({ fill: "rgb(255,255,255)" });
          }

          SVGObject.set({
            left: text.left - 10,
            top: text.top - 10,
          });

          const group = new fabric.Group([SVGObject, text], {
            scaleX: 0.5,
            scaleY: 0.5,
            selectable: true,
            evented: true,
            // whitebirdData: { id: uuid, type: "NOTE" }
          });

          this.addStickyNoteSettings(group);
          this.addTextBoxSettings(text, group);
          this.resetData();
          this.canvas.add(group).setActiveObject(group);
          this.canvas.renderAll();
        }
      );
    },
  },
};
</script>
