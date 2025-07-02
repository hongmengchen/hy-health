import { createApp } from 'vue'
import App from './App.vue'

// 引入路由和 Vuex 状态管理
import router from './router'
import store from './store'

// 引入其他依赖
import _ from 'lodash'  // 引入 lodash
import './style/reset.css'  // 引入样式
import 'animate.css'  // 引入动画库
import ElementPlus from 'element-plus'  // 引入 Element Plus
  import "element-plus/dist/index.css";

// 创建 Vue 应用实例
const app = createApp(App)

// 全局挂载 lodash
app.config.globalProperties._ = _

// 使用插件
app.use(ElementPlus)
app.use(router)
app.use(store)

// 挂载应用
app.mount('#app')
