<template>
  <!--  <img src="../assets/background.jpg" width="100%" height="100%"  sytle = "background-size: cover;background-attachment: fixed;background-position: center;">-->
  <div class="row vertical inset login" data-width="20rem">
    <img src="../assets/background.jpg" width="100%" height="100%" sytle = "background-size: cover;background-attachment: fixed;background-position: center;">
    <h2>Sign in to OWS</h2>
    <i class="fas fa-user-circle fa-5x" ></i> <!-- User icon-->
    <h2>帳號</h2>
    <input v-model="loginForm.username" type="text" placeholder="請輸入帳號"  />
    <h2>密碼</h2>
    <input v-model="loginForm.password" type="password" placeholder="請輸入密碼"  />
    <button type="primary" @click="handleLogin" >Login</button>
    <br>
  </div>
</template>

<style scoped>
@import "../assets/styles/loginstyle.css";
</style>
<script>
import { loginUserApi } from '@/apis/accountApi'
export default {
  name: 'Login',
  data () {
    return {
      loginForm: {
        username: '',
        password: ''
      }
    }
  },
  methods: {
    handleLogin () {
      const username = this.loginForm.username
      const password = this.loginForm.password
      // 帳號密碼需驗證不能為空
      if (username !== '' && password !== '') {
      } else {
        alert('帳號密碼不能為空')
      }
      console.log(this.loginForm)
      this.loginUser()
      this.redirectToBoard()
    },
    async loginUser () {
      try {
        await loginUserApi(this.loginForm)
        console.log('login success')
      } catch (err) {
        console.log(err)
      }
    },
    redirectToBoard () {
      this.$router.push({
        path: '/board',
        query: {
          userdata: this.loginForm
        }
      })
    }
  }
}

</script>
