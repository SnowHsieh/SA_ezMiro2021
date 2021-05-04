<template>
  <div class="home">
    <button @click='createBoardAndEnter()'>Create Board And Enter</button>
  </div>
</template>

<script>
import { CreateBoard } from '@/apis/Boards'
export default {
  name: 'Home',
  components: {
  },
  methods: {
    async createBoardAndEnter () {
      const boardId = await CreateBoard()
      this.$router.push({
        name: 'board',
        params: {
          boardId: boardId
        }
      })
    }
  }
}
</script>
