<template>
  <v-menu
    top
    :offset-x="true"
  >
    <template v-slot:activator="{ on, attrs }">
      <v-btn
        v-bind="attrs"
        v-on="on"
        @click="onClick"
      >
        <font-awesome-icon size="2x" :icon="['far', 'sticky-note']"/>
      </v-btn>
    </template>

    <v-list
      v-for="(item, index) in items"
      :key="index"
    >
      <v-button color="#00ff00">{{item.color}}</v-button>
    </v-list>
  </v-menu>

</template>
<script>
import eventbus from '@/utils/eventbus.js'
import { canvasToolMode } from '../event/event.js'

export default {
  name: 'NoteToolItem',
  data: () => ({
    items: [
      { title: 'Click Me', color: '#0000ff' },
      { title: 'Click Me', color: '#00ffff' },
      { title: 'Click Me', color: '#0055ff' },
      { title: 'Click Me 2', color: '#ff00ff' }
    ]
  }),
  methods: {
    onClick (e) {
      console.log(e)
      eventbus.emit(canvasToolMode.postNoteMode)
    }
  }
}
</script>
