import App from './App.vue'
import { createSSRApp } from 'vue'
import router from './router'  // 引入router
import store from './store'  // 引入vuex
import './uni.promisify.adaptor'
import "./style/reset.css"  // 引入样式

// 只创建一个应用实例
const app = createSSRApp(App)
app.use(store)
app.use(router)

// 导出应用实例
export { app }

// 直接挂载应用
app.mount('#app') // 确保你的index.html中有id为app的元素