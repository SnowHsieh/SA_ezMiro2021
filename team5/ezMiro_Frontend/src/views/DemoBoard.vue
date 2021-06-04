<template>
  <div>
    <div v-if="isActive">
      <button
        style="width: 100px; background-color: #cadf58"
        @click="changeColor('#CADF58')"
      >
        Read Model
      </button>
      <button
        style="width: 100px; background-color: #6cd5f5"
        @click="changeColor('#6CD5F5')"
      >
        Command
      </button>
      <button
        style="width: 100px; background-color: #fd9e4b"
        @click="changeColor('#FD9E4B')"
      >
        Domain Event
      </button>
      <button
        style="width: 100px; background-color: #c08ac9"
        @click="changeColor('#C08AC9')"
      >
        Policy
      </button>
      <button
        style="width: 100px; background-color: #fff9b2"
        @click="changeColor('#FFF9B2')"
      >
        Aggregate
      </button>
      <button @click="drawLines()">Line</button>
    </div>
    <div v-else>
      <button
        style="width: 100px; background-color: #cadf58"
        @click="createNotes('#CADF58')"
      >
        Read Model
      </button>
      <button
        style="width: 100px; background-color: #6cd5f5"
        @click="createNotes('#6CD5F5')"
      >
        Command
      </button>
      <button
        style="width: 100px; background-color: #fd9e4b"
        @click="createNotes('#FD9E4B')"
      >
        Domain Event
      </button>
      <button
        style="width: 100px; background-color: #c08ac9"
        @click="createNotes('#C08AC9')"
      >
        Policy
      </button>
      <button
        style="width: 100px; background-color: #fff9b2"
        @click="createNotes('#FFF9B2')"
      >
        Aggregate
      </button>
      <button @click="createLines()">Line</button>
    </div>
    <br />
    <div>
      <button style="width: 100px" @click="bringFigureForward()">
        Bring Figure Forward
      </button>
      <button style="width: 100px" @click="bringFigureToFront()">
        Bring Figure To Front
      </button>
      <button style="width: 100px" @click="sendFigureBackwards()">
        Send Figure Backwards
      </button>
      <button style="width: 100px" @click="sendFigureToBack()">
        Send Figure To Back
      </button>
    </div>
    <canvas id="canvas"></canvas>
    <div v-for="user in users" :key="user.name">
      <div
        v-if="user.name !== this.user.name"
        style="position: absolute"
        :style="{ top: user.y + 'px', left: user.x + 'px' }"
      >
        x<br />{{ user.name }}
      </div>
    </div>
  </div>
</template>

<script>
import { markRaw } from "@vue/reactivity";
import { fabric } from "fabric";
import Line from "@/model/line";

export default {
  name: "DemoBoard",
  data() {
    return {
      boardId: "",
      canvas: null,
      figures: {},
      activeObject: null,
      activeTextBox: null,
      isOverText: false,
      isEditingText: false,
      isActive: false,
      webSocket: null,
      user: null,
      users: {},
      isDrawLineModel: false,
    };
  },
  async mounted() {
    const boardId = this.$route.params.id;
    this.boardId = boardId;

    this.canvas = markRaw(
      new fabric.Canvas("canvas", {
        width: 1600,
        height: 900,
      })
    );

    this.user = {
      name: `匿名使用者${Math.floor(Math.random() * 1000) + 1}`,
      x: 0,
      y: 0,
    };
    this.webSocket = new WebSocket(
      `ws://localhost:8080/WebSocketServer/${this.boardId}/${this.user.name}`
    );
    this.webSocket.onopen = (e) => {
      console.log(e)
      console.log("WebSocket connected.");
    };
    this.webSocket.onmessage = async (e) => {
      const data = await JSON.parse(e.data);
      if (this.user.name === data.userId) {
        return;
      }
      if (data.type === "BoardEntered") {
        this.users[data.userId] = {
          name: data.userId,
          x: 0,
          y: 0,
        };
        setTimeout(() => {
          this.webSocket.send(
            JSON.stringify({ x: this.user.x, y: this.user.y })
          );
        }, 200);
      } else if (data.type === "CursorMoved") {
        let user = this.users[data.userId];

        if (user === undefined) {
          this.users[data.userId] = {
            name: data.userId,
            x: 100,
            y: 100,
          };
          user = this.users[data.userId];
        }

        user.x = data.positionX;
        user.y = data.positionY;
      } else if (data.type === "BoardLeft") {
        delete this.users[data.userId];
      } else if (data.type === "NotePosted") {
        if (
          this.activeObject !== null &&
          this.activeObject.figureId === data.figureId
        )
          return;

        data.text = "";
        this.drawNote(data);
        this.canvas.renderAll();
      } else if (data.type === "NoteMoved") {
        if (
          this.activeObject !== null &&
          this.activeObject.figureId === data.figureId
        )
          return;

        let note = this.figures[data.figureId];

        if (note === undefined) return;

        note.set({
          left: data.newLeftTopPositionY,
          top: data.newLeftTopPositionX,
        });

        this.canvas.renderAll();
      } else if (data.type === "NoteColorChanged") {
        if (
          this.activeObject !== null &&
          this.activeObject.figureId === data.figureId
        )
          return;

        let note = this.figures[data.figureId];

        if (note === undefined) return;

        note.item(0).set({
          fill: data.newColor,
          stroke: data.newColor,
        });

        this.canvas.renderAll();
      } else if (data.type === "NoteResized") {
        if (
          this.activeObject !== null &&
          this.activeObject.figureId === data.figureId
        )
          return;

        let note = this.figures[data.figureId];

        if (note === undefined) return;

        note.set({
          height: data.newHeight,
          width: data.newWidth,
        });

        note.item(0).set({
          height: data.newHeight,
          width: data.newWidth,
        });

        this.setTextBoxFontSize(note.item(1), note);

        this.canvas.renderAll();
      } else if (data.type === "NoteTextEdited") {
        if (
          this.activeObject !== null &&
          this.activeObject.figureId === data.figureId
        )
          return;

        let note = this.figures[data.figureId];

        if (note === undefined) return;

        note.item(1).set({
          text: data.newText,
        });

        this.setTextBoxFontSize(note.item(1), note);

        this.canvas.renderAll();
      } else if (data.type === "FigureDeleted") {
        let figure = this.canvas.getObjects().find((o) => {
          return o.figureId === data.figureId;
        });
        if (figure.figureType === "LINE") {
          this.canvas.remove(figure);
          this.canvas.remove(figure.endpointA)
          this.canvas.remove(figure.endpointB)
        } else {
          this.canvas.remove(figure);
        }
        delete this.figures[data.figureId];
      } else if (data.type === "LineDrew") {
        if (
          this.activeObject !== null &&
          this.activeObject.figureId === data.figureId
        ) {
          return;
        }
        this.drawLine(data);
        this.canvas.renderAll();
      } else if (data.type === "LineEndpointMoved") {
        if (
          this.activeObject !== null &&
          this.activeObject.figureId === data.figureId
        )
          return;
        let line = this.canvas.getObjects().find((o) => {
          return o.figureId === data.figureId;
        });
        if (line === undefined) return;
        if (line.endpointA.endpointId === data.endpointId) {
            line.endpointA.set({
                left: data.newPositionX,
                top: data.newPositionY
            })
            line.set({
                x1: data.newPositionX,
                y1: data.newPositionY
            })
        } else if (line.endpointB.endpointId === data.endpointId) {
            line.endpointB.set({
                left: data.newPositionX,
                top: data.newPositionY
            })
            line.set({
                x2: data.newPositionX,
                y2: data.newPositionY
            })
        }

        this.canvas.renderAll();
      }
      // if (data.type === 'CURSOR') {
      //     this.users = data.cursors
      // } else if (data.type === 'BOARD_CONTENT') {
      //     this.drawNotes(data.figures)
      //     console.log(data)
      // }
      // const users = await JSON.parse(e.data)
      // console.log(users)
      // this.users = users
    };
    this.canvas.on("mouse:move", (e) => {
      setTimeout(() => {
        this.webSocket.send(
          JSON.stringify({ x: e.absolutePointer.x, y: e.absolutePointer.y })
        );
      }, 200);
      this.user.x = e.absolutePointer.x;
      this.user.y = e.absolutePointer.y;
    });

    this.canvas.on("object:moving", (event) => {
      var target = event.target;
      setTimeout(() => {
        if (target.figureType === "NOTE") {
          this.$api.note.moveNote(target.figureId, target.top, target.left);
        } else if (target.figureType === "LINE_ENDPOINT") {
          this.$api.line.moveLineEndpoint(
            target.figureId,
            target.endpointId,
            target.left,
            target.top
          );
        }
      }, 200);
    });
    this.canvas.on("object:scaling", (event) => {
      var target = event.target;
      var height = target.height * target.scaleY;
      height = parseInt(Math.round(height), 10);
      var width = target.width * target.scaleX;
      width = parseInt(Math.round(width), 10);
      this.setTextBoxFontSize(target.item(1), target);
      this.$api.note.resizeNote(target.figureId, height, width);
      this.$api.note.moveNote(target.figureId, target.top, target.left);
    });
    window.addEventListener("keydown", (event) => {
      if (event.key === "Delete") {
        this.canvas.getActiveObjects().forEach((target) => {
          if (target.selectable) {
            console.log(target.figureType === "LINE_ENDPOINT");
            if (target.figureType === "LINE") {
              this.canvas.remove(target);
              this.canvas.remove(target.endpointA);
              this.canvas.remove(target.endpointB);
              this.$api.line.deleteLine(target.figureId);
            } else if (target.figureType === "LINE_ENDPOINT") {
              this.canvas.remove(target.line);
              this.canvas.remove(target.line.endpointA);
              this.canvas.remove(target.line.endpointB);
              this.$api.line.deleteLine(target.line.figureId);
            } else {
              this.canvas.remove(target);
              this.$api.note.deleteNote(target.figureId);
            }
          }
        });
      }
    });
    this.canvas.on("mouse:down", (event) => {
      if (this.isEditingText === true) {
        this.leaveEditingMode();
      }
      this.isActive = event.target !== null;
      this.activeObject = event.target;
    });

    this.getBoardContent();
    // this.drawLine({
    //     figureId: '',
    //     endpointADto: {
    //         positionX: 100,
    //         positionY: 100
    //     },
    //     endpointBDto: {
    //         positionX: 200,
    //         positionY: 200
    //     }
    // })
  },
  methods: {
    parseNote(note) {
      return {
        figureId: note.figureId,
        left: note.leftTopPositionY,
        top: note.leftTopPositionX,
        height: note.height,
        width: note.width,
        stroke: note.color,
        fill: note.color,
        text: note.text,
      };
    },
    drawNotes(notes) {
      if (notes !== null) {
        notes.forEach((note) => {
          if (note.figureType === "NOTE") {
            this.drawNote(note);
          }
        });
        this.canvas.renderAll();
      }
    },
    drawNote(note) {
      const parseNote = this.parseNote(note);
      const rect = new fabric.Rect({
        height: parseNote.height,
        width: parseNote.width,
        stroke: parseNote.stroke,
        fill: parseNote.fill,
        originX: "center",
        originY: "center",
      });

      const text = new fabric.Textbox(parseNote.text, {
        originX: "center",
        originY: "center",
      });

      const group = new fabric.Group([rect, text], {
        left: parseNote.left,
        top: parseNote.top,
        height: parseNote.height,
        width: parseNote.width,
        selectable: true,
        evented: true,
      });
      group.figureId = note.figureId;
      group.figureType = "NOTE";

      this.addNoteSettings(group);
      this.addTextBoxSettings(text, group);

      this.canvas.add(group);

      this.figures[group.figureId] = group;
    },
    async createNotes(color) {
      await this.$api.note.postNote(this.boardId, 200, 200, 100, 100, color);
      this.getBoardContent();
    },
    async createLines() {
      await this.$api.line.drawLine(
        this.boardId,
        { positionX: 100, positionY: 100, connectedFigureId: "" },
        { positionX: 200, positionY: 200, connectedFigureId: "" }
      );
      this.getBoardContent();
    },
    async getBoardContent() {
      this.canvas.clear();
      var figures = await this.$api.board.getBoardContent(this.boardId);
      this.drawNotes(figures);
      this.drawLines(figures);
    },
    drawLines(lines) {
      if (lines !== null) {
        lines.forEach((line) => {
          if (line.figureType === "LINE") {
            this.drawLine(line);
          }
        });
        this.canvas.renderAll();
      }
    },
    drawLine(lineDto) {
      const line = new Line(
        {
          id: lineDto.figureId,
          endpointA: {
            endpointId: lineDto.endpointA.endpointId,
            positionX: lineDto.endpointA.positionX,
            positionY: lineDto.endpointA.positionY,
            connectedFigureId: "",
          },
          endpointB: {
            endpointId: lineDto.endpointB.endpointId,
            positionX: lineDto.endpointB.positionX,
            positionY: lineDto.endpointB.positionY,
            connectedFigureId: "",
          },
        },
        {
          file: "red",
          stroke: "black",
          strokeWidth: 5,
          selectable: false,
          evented: true,
        }
      );
      this.figures[line.figureId] = line;
      this.canvas.add(line, line.endpointA, line.endpointB);
      this.canvas.renderAll();
    },
    changeColor(color) {
      this.activeObject.item(0).set({
        fill: color,
        stroke: color,
      });
      this.$api.note.changeNoteColor(this.activeObject.figureId, color);
      this.canvas.renderAll();
    },
    addTextBoxSettings(textBox, group) {
      textBox.on("mouseover", () => {
        this.isOverText = true;
      });

      textBox.on("mouseout", () => {
        this.isOverText = false;
      });

      textBox.on("changed", () => {
        this.editNoteText(textBox, group);
      });

      textBox.set({
        hasControls: false,
        hasBorders: false,
        lockMovementX: true,
        lockMovementY: true,
        textAlign: "center",
      });
      this.setTextBoxFontSize(textBox, group);
    },
    addNoteSettings(group) {
      const invisibleControls = ["mt", "mr", "ml", "mb", "mtr"];
      invisibleControls.forEach((side) => {
        group.setControlVisible(side, false);
      });

      group.on("mousedblclick", () => {
        group.set({
          selectable: false,
          evented: false,
        });
        this.isEditingText = true;
        this.activeObject = group;

        this.activeTextBox = group.item(1);
        this.activeTextBox.scaleX *= group.scaleX;
        this.activeTextBox.scaleY *= group.scaleY;
        this.activeTextBox.left =
          group.left + (group.height / 2) * this.activeTextBox.scaleX;
        this.activeTextBox.top =
          group.top + (group.width / 2) * this.activeTextBox.scaleY;

        group.remove(group.item(1));
        this.canvas.add(this.activeTextBox).setActiveObject(this.activeTextBox);
        this.activeTextBox.enterEditing();

        this.canvas.renderAll();
      });
    },
    leaveEditingMode() {
      if (this.isEditingText === true && this.isOverText === false) {
        this.activeTextBox.exitEditing();

        this.canvas.remove(this.activeTextBox);

        this.activeObject.addWithUpdate(this.activeTextBox);

        this.canvas.setActiveObject(this.activeObject);

        this.isEditingText = false;
        this.activeObject.set({
          selectable: true,
          evented: true,
        });

        this.resetData();
        this.canvas.renderAll();
      }
    },
    setTextBoxFontSize(textBox, group) {
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

      const maxFixedWidth = group.item(0).width * 0.9;
      const maxFiexdHeight = group.item(0).height * 0.9;
      const maxFontSize = group.item(0).height * 0.9;

      let newFontSize = textBox.fontSize;

      newFontSize *= maxFixedWidth / (textBox.width + 1);
      if (newFontSize > maxFontSize) {
        newFontSize = maxFontSize;
        // textBox.set({ fontSize: maxFontSize })
      } else {
        // textBox.set({ fontSize: newFontSize })
      }
      textBox.width = maxFixedWidth;

      while (textBox.height > maxFiexdHeight) {
        const scale = textBox.height / maxFiexdHeight;
        if (newFontSize > maxFontSize) {
          newFontSize = maxFontSize;
        }
        newFontSize -= scale;

        textBox.set({ fontSize: newFontSize });
      }

      textBox.set({ fontSize: newFontSize });
    },
    editNoteText(textBox, group) {
      this.setTextBoxFontSize(textBox, group);
      this.$api.note.editNoteText(group.figureId, textBox.text);
      this.canvas.renderAll();
    },
    resetData() {
      this.groupObject = null;
      this.textBox = null;
    },
    async bringFigureForward() {
      this.$api.board.bringFigureForward(
        this.boardId,
        this.activeObject.figureId
      );
      await this.canvas.bringForward(this.activeObject);
      this.canvas.renderAll();
    },
    async bringFigureToFront() {
      this.$api.board.bringFigureToFront(
        this.boardId,
        this.activeObject.figureId
      );
      await this.canvas.bringToFront(this.activeObject);
      this.canvas.renderAll();
    },
    async sendFigureBackwards() {
      this.$api.board.sendFigureBackwards(
        this.boardId,
        this.activeObject.figureId
      );
      await this.canvas.sendBackwards(this.activeObject);
      this.canvas.renderAll();
    },
    async sendFigureToBack() {
      this.$api.board.sendFigureToBack(
        this.boardId,
        this.activeObject.figureId
      );
      await this.canvas.sendToBack(this.activeObject);
      this.canvas.renderAll();
    },
  },
};
</script>
