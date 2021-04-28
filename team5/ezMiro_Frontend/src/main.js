import { createApp } from 'vue'
import App from './App.vue'
import { library } from '@fortawesome/fontawesome-svg-core'
import { fas } from '@fortawesome/free-solid-svg-icons/'
import { far } from '@fortawesome/free-regular-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import router from './router'
import fabric from 'fabric'
import api from './apis'


library.add([
  fas.faDownload,
  fas.faUserPlus,
  fas.faMousePointer,
  fas.faPencilAlt,
  fas.faEraser,
  fas.faPalette,
  fas.faShapes,
  fas.faSquare,
  far.faSquare,
  fas.faCircle,
  far.faCircle,
  fas.faFont,
  fas.faBorderAll,
  fas.faStickyNote,
])

const app = createApp(App)
app.component("font-awesome-icon", FontAwesomeIcon)
app.use(router)
app.use(fabric)
app.mount("#app")
app.config.globalProperties.$api = api
