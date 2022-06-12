<template>
  <!--  <img src="../assets/background.jpg" width="100%" height="100%"  sytle = "background-size: cover;background-attachment: fixed;background-position: center;">-->
  <div class="row vertical inset login" data-width="20rem">
    <img src="../assets/background.jpg" width="100%" height="100%" sytle = "background-size: cover;background-attachment: fixed;background-position: center;">
    <h2>Sign in to OWS</h2>
    <i class="fas fa-user-circle fa-5x" ></i> <!-- User icon-->
    <h2>使用者信箱</h2>
    <input v-model="registerForm.email" type="text" placeholder="請輸入註冊信箱"  />
    <h2>帳號</h2>
    <input v-model="registerForm.username" type="text" placeholder="請輸入帳號"  />
    <h2>密碼</h2>
    <input v-model="registerForm.password" type="password" placeholder="請輸入密碼"  />
    <h2>密碼確認</h2>
    <input v-model="registerForm.confirmPassword" type="password" placeholder="請再輸入一次密碼"  />
    <button type="primary" @click="handleRegister" >Register</button>
    <br>
  </div>
</template>

<style scoped>
@import "../assets/styles/signupstyle.css";
</style>
<script>
import { registerUserApi } from '@/apis/accountApi'
export default {
  name: 'Register',
  data () {
    return {
      registerForm: {
        email: '',
        username: '',
        password: '',
        confirmPassword: ''
      }
    }
  },
  methods: {
    handleRegister () {
      const username = this.registerForm.username
      const password = this.registerForm.password
      const confirmPassword = this.registerForm.confirmPassword
      const email = this.registerForm.email
      // 帳號密碼需驗證不能為空
      if (username === '' || password === '' || confirmPassword === '' || email === '') {
        alert('註冊欄位不得為空')
      } else if (password !== confirmPassword) {
        alert('密碼需與確認密碼相同!')
      } else {
        this.registerUser()
      }
      console.log(this.registerForm)
    },
    async registerUser () {
      try {
        const result = await registerUserApi(this.registerForm)
        console.log(result)
        if (result.data.message === 'Register success') {
          alert('註冊成功!即將跳轉登入頁')
          this.redirectToLogin()
        }
      } catch (err) {
        console.log(err)
      }
    },
    redirectToLogin () {
      this.$router.push({
        path: '/login'
      })
    }
  }
}

</script>
