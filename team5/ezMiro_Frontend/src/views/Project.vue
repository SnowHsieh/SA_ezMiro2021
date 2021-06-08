<template>
  <v-container>
    <v-row>
      <v-col
        v-for="n in boardCount"
        :key="n"
        cols="12"
      >
        <v-item v-slot="{ active, toggle }">
          <v-card
            :color="active ? 'primary' : ''"
            class="d-flex align-center"
            height="200"
            @click="toggle(); gotoBoard();"
          >
            <v-scale-transition>
              <div
                class="flex-grow-1 text-center"
              >
                Board Id: {{ boardId }}
              </div>
            </v-scale-transition>
          </v-card>
        </v-item>
      </v-col>
    </v-row>
  </v-container>
</template>
<script>
import { board } from '@/utils/apis.js'

export default {
  name: 'Project',
  data: () => ({
    boardCount: 1,
    boardId: ''
  }),
  async created () {
    this.boardId = await board.createBoard('projectId', 'boardName')
  },
  methods: {
    gotoBoard () {
      this.$router.push(`/board/${this.boardId}`)
    }
  }
}
</script>
