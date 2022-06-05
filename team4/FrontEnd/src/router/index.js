import Vue from 'vue'
import VueRouter from 'vue-router'
import Board from '../components/Board.vue'
import Login from '../components/Login.vue'
Vue.use(VueRouter)
// const router = new VueRouter(
//   {
//     mode: 'hash',
//     base: process.env.BASE_URL,
//     routes
//   }
// )
//
// export default router
export default new VueRouter({
  routes: [
    {
      path: '/board',
      component: Board
    },
    {
      path: '/login',
      component: Login
    }
  ]
})
