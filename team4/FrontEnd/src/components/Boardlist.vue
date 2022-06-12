<template>
  <!--  <img src="../assets/background.jpg" width="100%" height="100%"  sytle = "background-size: cover;background-attachment: fixed;background-position: center;">-->
  <div class="row vertical inset login" data-width="20rem">
    <img src="../assets/background.jpg" width="100%" height="100%" sytle = "background-size: cover;background-attachment: fixed;background-position: center;">
    <i class="fas fa-user-circle fa-5x" ></i> <!-- User icon-->

    <h2>看板列表</h2>
    <ul id="example-1">
      <li v-for="item in boardListForm.boardList" :key="item">
        <button  @click="redirectToBoard(item)" >{{item}}</button>
      </li>
    </ul>
    <h2>---------------------</h2>
    <input v-model="teamForm.teamName" type="text" placeholder="請輸入欲新增的白板名稱"  />
    <button type="primary" @click="handleCreateTeam" >CreateTeam</button>

    <br>
  </div>
</template>
<style scoped>
</style>
<script>
import { createTeamApi, getBoardIdInTeamApi } from '@/apis/teamApi'

export default {
  name: 'Boardlist',
  async mounted () {
    this.receiveUserData()
    await this.getBoardIdInfoOfUser()
  },
  data () {
    return {
      boardListForm: {
        boardList: '',
        username: ''
      },
      teamForm: {
        teamName: '',
        username: ''
      }
    }
  },
  methods: {
    receiveUserData () {
      this.boardListForm.username = this.$route.query.userName
      this.teamForm.username = this.$route.query.userName
      console.log(this.boardListForm)
    },
    handleCreateTeam () {
      const teamName = this.teamForm.teamName

      // 帳號密碼需驗證不能為空
      if (teamName === '') {
        alert('看板名稱不能為空')
      }
      console.log(this.loginForm)
      this.createTeam()
    },
    async createTeam () {
      try {
        const result = await createTeamApi(this.teamForm)
        console.log(result)
        if (result.data.message === 'create team success') {
          this.$router.go(0)
        } else {
          alert('此團隊名稱已存在，請重新嘗試!')
        }
      } catch (err) {
        console.log(err)
      }
    },
    async getBoardIdInfoOfUser () {
      try {
        const result = await getBoardIdInTeamApi(this.boardListForm.username)
        console.log(result)
        // const parsedResult = JSON.parse(result.data.message)
        this.boardListForm.boardList = result
      } catch (err) {
        console.log(err)
      }
    },
    redirectToBoard (boardId) {
      this.$router.push({
        query: { boardId: boardId },
        path: '/board'
      })
    }
  }
}

</script>
