import { createApp } from 'vue'
import App from './App.vue'

// 引入路由和 Vuex 状态管理
import router from './router'
import store from './store'

// 引入其他依赖
import _ from 'lodash'
import './style/reset.css'
import 'animate.css'
import ElementPlus from 'element-plus'

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
