import Home from '../views/Home.vue'
import Board from '../views/Board.vue'
import DemoBoard from '../views/DemoBoard.vue'

export const routes = [
  {
    path: '/',
    name: 'home',
    component: Home
  },
  {
    path: '/board',
    name: 'board',
    component: Board
  },
  {
    path: '/demoboard',
    name: 'demoboard',
    component: DemoBoard
  }
]
