<template>
  <v-navigation-drawer
    v-model="drawer"
    app
    class="pt-4"
    color="grey lighten-3"
    mini-variant
    floating
    permanent
  >
    <v-list>
      <v-list-item class="px-1">
        <v-btn-toggle mandatory>
          <mouse-tool-item ref="mouseToolItem"/>
          <note-tool-item/>
          <line-tool-item/>
        </v-btn-toggle>
      </v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>

<script>
import MouseToolItem from './toolitems/MouseToolItem.vue'
import NoteToolItem from './toolitems/NoteToolItem.vue'
import eventbus from '@/utils/eventbus.js'
import event from './event/event.js'
import LineToolItem from './toolitems/LineToolItem.vue'

export default {
  name: 'CanvasToolBar',
  components: { MouseToolItem, NoteToolItem, LineToolItem },
  data: () => ({
    drawer: false
  }),
  mounted () {
    eventbus.on(event.canvasEvent.mouseDown, () => {
      this.$refs.mouseToolItem.$refs.btn.click('')
    })
  }
}
</script>
<style scoped>
  .v-btn-toggle {
    flex-direction: column;
  }
</style>
