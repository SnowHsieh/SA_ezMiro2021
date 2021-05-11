<template>
  <div class="board-container">
    <div class="container-content">
      <UiWhiteboardControls />
      <UiWhiteboard />
    </div>
  </div>
</template>

<script>
import UiWhiteboardControls from "@/components/panel/UiWhiteboardControls.vue";
import UiWhiteboard from "@/components/UiWhiteboard.vue";

export default {
  components: {
    UiWhiteboardControls,
    UiWhiteboard,
  },
};
</script>
