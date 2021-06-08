<template>
  <v-btn @click="onClick" ref="btn">
    <font-awesome-icon size="2x" :icon="['fas', 'mouse-pointer']"/>
  </v-btn>
</template>
<script>
import eventbus from '@/utils/eventbus.js'
import { canvasToolMode } from '../event/event.js'

export default {
  name: 'MouseToolItem',
  methods: {
    onClick () {
      eventbus.emit(canvasToolMode.mouseMode)
    }
  },
  test: (e) => {
    console.log(e)
  }
}
</script>
