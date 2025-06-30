import App from './App'

// #ifndef VUE3
import Vue from 'vue'
import store from './store' // 引入vuex
import './uni.promisify.adaptor'
import _ from 'lodash' // js库
import './static/reset.css' // 引入样式
import 'animate.css' // 引入动画库

// 注册全局工具函数
Vue.prototype._ = _

Vue.config.productionTip = false
App.mpType = 'app'
const app = new Vue({
  ...App
})
app.$mount()
// #endif

// #ifdef VUE3
import { createSSRApp } from 'vue'
export function createApp() {
  const app = createSSRApp(App)
  return {
    app
  }
}
// #endif