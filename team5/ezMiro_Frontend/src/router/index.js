import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path: '/board/:id',
    name: 'Board',
    components: {
      default: () => import('@/views/Board.vue'),
      side_navigation: () => import('@/components/board/CanvasToolBar.vue')
    }
  },
  {
    path: '/project',
    name: 'Project',
    components: {
      default: () => import('@/views/Project.vue'),
      side_navigation: () => import('@/components/project/ProjectToolBar')
    }
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
