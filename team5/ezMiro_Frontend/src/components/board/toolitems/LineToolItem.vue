<template>
  <v-btn @click="onClick" ref="btn">
    <v-icon large>
      mdi-vector-line
    </v-icon>
  </v-btn>
</template>
<script>
import eventbus from '@/utils/eventbus.js'
import { canvasToolMode } from '../event/event.js'

export default {
  name: 'LineToolItem',
  methods: {
    onClick () {
      eventbus.emit(canvasToolMode.drawLineMode)
    }
  },
  test: (e) => {
    console.log(e)
  }
}
</script>
