<template>
  <div class="home">
    <div class="enterBoard">
      <input v-model="userName" placeholder="請輸入暱稱">
      <input v-model="boardId" placeholder="請輸入看板ID">
      <button @click='createBoardAndEnter()'>Create Board And Enter</button>
    </div>
  </div>
</template>

<script>
import { CreateBoard } from '@/apis/Boards'
export default {
  name: 'Home',
  components: {
  },
  data () {
    return {
      userName: '',
      boardId: ''
    }
  },
  methods: {
    async createBoardAndEnter () {
      if (this.boardId === '') {
        this.boardId = await CreateBoard()
      }
      this.$router.push({
        name: 'Board',
        params: {
          boardId: this.boardId,
          userName: this.userName
        }
      })
    }
  }
}
</script>
