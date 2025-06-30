import { login } from "../../api/Login";
const state = {
  token: "",
};
const mutations = {
  SET_TOKEN(state, payload) {
    state.token = payload;
  }
};
const actions = {
  // 登录接口
  login({ commit }, loginInfo) {
    const username = loginInfo.username.trim();
    return new Promise((resolve, reject) => {
      login(username, loginInfo.password).then((res) => {
        if (res.data.code == "20000") {
          uni.showToast({
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
  }
};
export default {
  namespaced: true,
  state,
  mutations,
  actions,
};