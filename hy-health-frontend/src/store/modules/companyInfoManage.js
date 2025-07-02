import {
    getCompanyInfo,
    addCompany,
    deleteCompany,
    modifyCompanyInfo,
    getAllCompanyInfo,
  } from "@/api/admin/companyInfoManage";

  const state = {
    companyInfo: {}, //公司信息
  };
  const mutations = {
    GET_COMPANY_INFO(state, payload) {
      state.companyInfo = payload;
    },
  };
  const actions = {
    // 公司信息分页，关键字查询
    getCompanyInfo({ commit }, { pn, size, keyword }) {
      getCompanyInfo(pn, size, keyword).then((res) => {
        if (res) {
          commit("GET_COMPANY_INFO", res.data.data.pageInfo);
        }
      });
    },
    // 新增公司
    addCompany({ dispatch }, { companyName, companyPhone, size }) {
      addCompany(companyName, companyPhone).then((res) => {
        // 新增之后跳转到最后一页s
        dispatch("getCompanyInfo", { pn: res.data.data.pages, size });
      });
    },
    // 删除公司信息
    deleteCompany({ dispatch }, { companyId, pn, size, keyword }) {
      deleteCompany(companyId).then(() => {
        dispatch("getCompanyInfo", { pn, size, keyword });
      });
    },
    // 修改公司信息
    modifyCompanyInfo(
      { dispatch },
      { companyId, companyName, companyPhone, pn, size, keyword }
    ) {
      modifyCompanyInfo(companyId, companyName, companyPhone).then(() => {
        dispatch("getCompanyInfo", { pn, size, keyword });
      });
    },
    // 查询所有公司信息
    getAllCompanyInfo({ commit }) {
      getAllCompanyInfo().then((res) => {
        commit("GET_COMPANY_INFO", res.data.data.pageInfo);
      });
    },
  };
  export default {
    namespaced: true,
    state,
    mutations,
    actions,
  };