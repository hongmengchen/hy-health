import { createStore } from 'vuex'

export default createStore({
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