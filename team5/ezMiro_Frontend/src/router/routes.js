import Home from '../views/Home.vue'
import Board from '../views/Board.vue'
import DemoBoard from '../views/DemoBoard.vue'
import DemoProject from '../views/DemoProject.vue'

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
    path: '/demoboard/:id',
    name: 'demoboard',
    component: DemoBoard
  },
  {
    path: '/demoproject',
    name: 'demoproject',
    component: DemoProject
  }
]
