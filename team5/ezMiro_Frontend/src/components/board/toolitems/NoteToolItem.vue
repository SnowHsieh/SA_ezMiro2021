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
    <v-btn-toggle
      v-model="toggle_one"
      mandatory
    >
      <v-btn
        v-for="(item, index) in items"
        :key="index"
      >
        <font-awesome-icon size="2x" :icon="['fas', 'sticky-note']" :style="{ color: item.color }"/>
      </v-btn>
    </v-btn-toggle>
  </v-menu>
</template>
<script>
import eventbus from '@/utils/eventbus.js'
import { canvasToolMode } from '../event/event.js'

export default {
  name: 'NoteToolItem',
  data: () => ({
    toggle_one: 0,
    items: [
      { title: 'Read Model', color: '#CADF58' },
      { title: 'Command', color: '#6CD5F5' },
      { title: 'Domain Event', color: '#FD9E4B' },
      { title: 'Policy', color: '#C08AC9' },
      { title: 'Aggregate', color: '#FFF9B2' },
      { title: 'User', color: '#FEF445' }
    ]
  }),
  methods: {
    onClick () {
      eventbus.emit(canvasToolMode.postNoteMode, {
        color: this.items[this.toggle_one].color
      })
    }
  },
  watch: {
    toggle_one: function (newToggleOne) {
      eventbus.emit(canvasToolMode.postNoteMode, {
        color: this.items[newToggleOne].color
      })
    }
  }
}
</script>
<style scoped>
.v-btn-toggle {
  flex-direction: column;
}
</style>
