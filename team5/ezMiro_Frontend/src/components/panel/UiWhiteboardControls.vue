<template>
  <div class="ui-whiteboard-controls component">
    <div class="toolbar--box--top-left">
      <div class="logo--box">
        <a href="/"><img src="../../assets/images/logo.svg" alt="logo" /></a>
      </div>
      <div class="toolbar toolbar--big flex mr--1">
        <div class="toolbar--board toolbar--board--item flex">
          <div>
            <input
              :readonly="true"
              class="toolbar--board--name"
              value="Event storming practices"
            />
          </div>

          <!-- Export button -->
          <div
            class="toolbar--board toolbar--board--pdf flex--middle"
            @click="toggleExportDropdown"
          >
            <font-awesome-icon
              icon="download"
              size="lg"
              class="toolbar--button--icon mgl0 mgr15"
            />
          </div>

          <!-- Export menu -->
          <div
            v-if="isExportActionsOpened"
            class="dropdown dropdown--toolbar fadeInUp"
          >
            <ul>
              <li class="dropdown--menu--item">
                <a href="#" class="dropdown--menu--link"> Export as PDF</a>
              </li>
              <li class="dropdown--menu--item">
                <a href="#" class="dropdown--menu--link"> Export as image</a>
              </li>
            </ul>
          </div>
        </div>
      </div>

      <!-- Colloboration button -->
      <div class="toolbar--button toolbar--button--colored toolbar--big">
        <font-awesome-icon icon="user-plus" class="toolbar--button--icon" />
        <span>Invite</span>
      </div>

      <div class="toolbar-box-middle-left">
        <div>
          <div class="toolbar toolbar--vertical">
            <ul class="tools--menu mgt-0 mgb-0">
              <!-- Pointer -->
              <li class="tools--item">
                <div
                  class="tools--item--button"
                  @click="toggleMousePointerToolbox"
                >
                  <font-awesome-icon icon="mouse-pointer" />
                </div>
              </li>
              <!-- Pencil -->
              <li class="tools--item">
                <div class="tools--item--button" @click="togglePencilToolbox">
                  <font-awesome-icon icon="pencil-alt" />
                </div>
              </li>
              <!-- Slider to choose pencil size -->
              <!-- End -->

              <!-- Eraser -->
              <!-- <li class="tools--item">
                <div class="tools--item--button">
                  <font-awesome-icon icon="eraser" />
                </div>
              </li> -->

              <!-- Color palette -->
              <!-- <li class="tools--item">
                <div class="tools--item--button">
                  <font-awesome-icon icon="palette" />
                  <div
                    :style="{ background: colorPicked }"
                    class="color--picked"
                  ></div>
                </div>
              </li> -->

              <!-- Shape -->
              <li class="tools--item">
                <div class="tools--item--button" @click="toggleShapeToolbox">
                  <font-awesome-icon
                    v-if="
                      shapeIsSelected[0] === 'fas' &&
                      shapeIsSelected[1] === 'square'
                    "
                    :icon="shapeIsSelected"
                  />
                  <font-awesome-icon
                    v-else-if="
                      shapeIsSelected[0] === 'far' &&
                      shapeIsSelected[1] === 'square'
                    "
                    :icon="shapeIsSelected"
                  />
                  <font-awesome-icon
                    v-else-if="
                      shapeIsSelected[0] === 'fas' &&
                      shapeIsSelected[1] === 'circle'
                    "
                    :icon="shapeIsSelected"
                  />
                  <font-awesome-icon
                    v-else-if="
                      shapeIsSelected[0] === 'far' &&
                      shapeIsSelected[1] === 'circle'
                    "
                    :icon="shapeIsSelected"
                  />
                </div>
              </li>
              <div v-if="isShapeToolBoxOpened" class="toolbox fadeInLeft">
                <ul class="tools--menu tools--menu--inline">
                  <!-- Rectangle -->
                  <li class="tools--item mgt-0" @click="toggleRectangle">
                    <div class="tools--item--button mgt-0">
                      <font-awesome-icon :icon="['far', 'square']" />
                    </div>
                  </li>
                  <!-- Rectangle pre filled -->
                  <li class="tools--item mgt-0" @click="toggleRectangleFilled">
                    <div class="tools--item--button mgt-0">
                      <font-awesome-icon :icon="['fas', 'square']" />
                    </div>
                  </li>
                  <!-- Circle -->
                  <li class="tools--item mgt-0" @click="toggleCircle">
                    <div class="tools--item--button mgt-0">
                      <font-awesome-icon :icon="['far', 'circle']" />
                    </div>
                  </li>
                  <!-- Circle pre filled -->
                  <li class="tools--item mgt-0" @click="toggleCircleFilled">
                    <div class="tools--item--button mgt-0">
                      <font-awesome-icon :icon="['fas', 'circle']" />
                    </div>
                  </li>
                </ul>
              </div>

              <!-- Text -->
              <li class="tools--item">
                <div class="tools--item--button" @click="toggleTextBox">
                  <font-awesome-icon icon="font" />
                </div>
              </li>

              <!-- Sticky notes -->
              <li class="tools--item">
                <div class="tools--item--button" @click="toggleStickyNotes">
                  <font-awesome-icon icon="sticky-note" />
                </div>
              </li>
              <div
                v-if="isStickyNoteToolBoxOpened"
                class="toolbox fadeInLeft top170"
              >
                <ul class="tools--menu tools--menu--inline pd5">
                  <li class="tools--item mgt-0">
                    <div
                      class="tools--item--button mg25-5"
                      @click="selectColor('#000000')"
                    >
                      <img
                        src="../../assets/images/stickynote/black.svg"
                        alt="black"
                      />
                    </div>
                    <div 
                      class="tools--item--button mg25-5"
                      @click="selectColor('#8CB8DE')">
                      <img
                        src="../../assets/images/stickynote/blue.svg"
                        alt="blue"
                      />
                    </div>
                    <div 
                      class="tools--item--button mg25-5"
                      @click="selectColor('#58CA68')">
                      <img
                        src="../../assets/images/stickynote/green.svg"
                        alt="green"
                      />
                    </div>
                  </li>
                  <li class="tools--item mgt-0">
                    <div 
                      class="tools--item--button mg25-5"
                      @click="selectColor('#8A0000')">
                      <img
                        src="../../assets/images/stickynote/red.svg"
                        alt="red"
                      />
                    </div>
                    <div 
                      class="tools--item--button mg25-5"
                      @click="selectColor('#FFD54F')">
                      <img
                        src="../../assets/images/stickynote/yellow.svg"
                        alt="yellow"
                      />
                    </div>
                    <div 
                      class="tools--item--button mg25-5"
                      @click="selectColor('#FFFFFF')">
                      <img
                        src="../../assets/images/stickynote/white.svg"
                        alt="white"
                      />
                    </div>
                  </li>
                </ul>
              </div>
              <!-- Sticky notes -->

              <!-- Background -->
              <li class="tools--item">
                <div 
                  class="tools--item--button"
                  @click="swapBackground">
                  <font-awesome-icon icon="border-all" />
                </div>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import customEvents from "../../utils/customEvents";
import EventBus from "../../EventBus";

export default {
  name: "UiWhiteboardControls",
  data() {
    return {
      isShapeToolBoxOpened: false,
      isExportActionsOpened: false,
      isStickyNoteToolBoxOpened: false,
      colorPicked: "black",
      shapeIsSelected: ["fas", "square"],
      imageBackgroundIndex: 0,
    };
  },
  mounted() {
    this.isShapeToolBoxOpened = false;
    this.isExportActionsOpened = false;
    this.isStickyNoteToolBoxOpened = false;
  },
  methods: {
    toggleMousePointerToolbox() {
      EventBus.emit(customEvents.canvasTools.drawing, { drawingMode: false });
      this.isShapeToolBoxOpened = false;
      this.isExportActionsOpened = false;
      this.isStickyNoteToolBoxOpened = false;
    },
    togglePencilToolbox() {
      EventBus.emit(customEvents.canvasTools.drawing, { drawingMode: true });
      this.isShapeToolBoxOpened = false;
      this.isExportActionsOpened = false;
      this.isStickyNoteToolBoxOpened = false;
    },
    toggleExportDropdown() {
      this.isExportActionsOpened = !this.isExportActionsOpened;
      this.isShapeToolBoxOpened = false;
      this.isStickyNoteToolBoxOpened = false;
    },
    toggleShapeToolbox() {
      this.isShapeToolBoxOpened = !this.isShapeToolBoxOpened;
      this.isExportActionsOpened = false;
      this.isStickyNoteToolBoxOpened = false;
    },
    toggleRectangle() {
      this.shapeIsSelected = ["far", "square"];
      EventBus.emit(customEvents.canvasTools.rectangle, {
        stroke: this.colorPicked,
        fill: "",
      });
    },
    toggleRectangleFilled() {
      this.shapeIsSelected = ["fas", "square"];
      EventBus.emit(customEvents.canvasTools.rectangle, {
        fill: this.colorPicked,
      });
    },
    toggleCircle() {
      this.shapeIsSelected = ["far", "circle"];
      EventBus.emit(customEvents.canvasTools.circle, {
        stroke: this.colorPicked,
        fill: "",
      });
    },
    toggleCircleFilled() {
      this.shapeIsSelected = ["fas", "circle"];
      EventBus.emit(customEvents.canvasTools.circle, {
        fill: this.colorPicked,
      });
    },
    toggleTextBox() {
      EventBus.emit(customEvents.canvasTools.textbox, {});
    },
    toggleStickyNotes() {
      this.isStickyNoteToolBoxOpened = !this.isStickyNoteToolBoxOpened;
      this.isShapeToolBoxOpened = false;
      this.isExportActionsOpened = false;
    },
    selectColor(color) {
      EventBus.emit(customEvents.canvasTools.stickyNote, { color });
    },
    swapBackground() {
      if (this.imageBackgroundIndex === 3) {
        this.imageBackgroundIndex = 0;
      }

      const states = ['eisenhower', 'blank', 'dots'];
      const element = states[this.imageBackgroundIndex];
      EventBus.emit(customEvents.canvas.imageBackgroundChanged, element);
      this.imageBackgroundIndex += 1;
    }
  },
};
</script>

<style scoped>
@import "../../assets/scss/panel/_whiteboardcontrols.scss";
</style>
