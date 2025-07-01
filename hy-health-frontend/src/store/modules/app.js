import { login } from "@/api/login";
import { ElMessage } from "element-plus";
import router, { constantRoutes } from '../../router/index'
import {getMenu} from '../../utils/routeParse'

const state = {
  token: "",
  menuList: [] //路由列表
};

const mutations = {
  SET_TOKEN(state, payload) {
    state.token = payload;
  },

  //存储完整的路由
  SET_ROUTER_MENULIST(state, payload) {
    // 把固定路由和后端传来的路由合并为完整路由
    const array = constantRoutes.concat(payload)
    state.menuList = array
    router.options.routes = array
    array.forEach(route => {
      router.addRoute(route)
    })
  }
};
const actions = {
  // 登录接口
  login({ commit }, loginInfo) {
    const username = loginInfo.username.trim();
    return new Promise((resolve, reject) => {
      login(username, loginInfo.password).then((res) => {
        if (res.data.code == "20000") {
          ElMessage({
            type: "success",
            message: "登录成功",
          });
          localStorage.setItem('userInfo', JSON.stringify(res.data.data.userInfo))
          localStorage.setItem("token", res.data.data.token);
          commit("SET_TOKEN", res.data.data.token);
          resolve();
        } else {
          reject();
        }
      });
    });
  },

  // 获取后端传来的路由列表
  setMenuList({ commit }) {
      return new Promise((resolve) => {
        getMenu().then(res => {
          commit('SET_ROUTER_MENULIST', res)
          resolve(res)
        })
      })
  }
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
};