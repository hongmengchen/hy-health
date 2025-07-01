// store/index.js
import { createStore } from 'vuex'
import getters from "./getters";
import app from './modules/app' // 导入 app 模块


export default createStore({  modules: { app },
  state() {
    return {
      userInfo: null
    }
  },
  
  mutations: {
    SET_USER_INFO(state, userInfo) {
      state.userInfo = userInfo
    }
  },
  
  actions: {
    login({ commit }, userInfo) {
      // 登录逻辑
      return new Promise((resolve, reject) => {
        // 模拟API调用
        setTimeout(() => {
          commit('SET_USER_INFO', userInfo)
          resolve()
        }, 1000)
      })
    },
    setMenuList({ commit }) {
      // 设置菜单逻辑
    }
  }
})