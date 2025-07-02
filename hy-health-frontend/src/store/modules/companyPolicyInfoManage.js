import {
    getCompanyPolicyInfo,
    addCompanyPolicy,
    deleteCompanyPolicy,
    modifyCompanyPolicyInfo,
} from "@/api/admin/companyPolicyInfoManage";

const state = {
    companyPolicyInfo: {}, //医药公司政策管理信息
};
const mutations = {
    GET_COMPANY_POLICY_INFO(state, payload) {
        state.companyPolicyInfo = payload;
    },
};
const actions = {
    // 医药公司政策管理信息分页，关键字查询
    getCompanyPolicyInfo({commit}, {pn, size, keyword}) {
        getCompanyPolicyInfo(pn, size, keyword).then((res) => {
            if (res) {
                commit("GET_COMPANY_POLICY_INFO", res.data.data.policyInfo);
            }
        });
    },
    // 新增医药公司政策管理信息
    addCompanyPolicy({dispatch}, {companyId, message, title, size}) {
        addCompanyPolicy(companyId, message, title).then((res) => {
            // 新增之后跳转到最后一页
            dispatch("getCompanyPolicyInfo", {pn: res.data.data.pages, size});
        });
    },
    // 删除
    deleteCompanyPolicy({dispatch}, {id, pn, size, keyword}) {
        deleteCompanyPolicy(id).then(() => {
            dispatch("getCompanyPolicyInfo", {pn, size, keyword});
        });
    },
    //  修改
    modifyCompanyPolicyInfo(
        {dispatch},
        {id, companyId, title, message, pn, size}
    ) {
        modifyCompanyPolicyInfo(id, companyId, title, message).then(() => {
            dispatch("getCompanyPolicyInfo", {pn, size});
        });
    },
};

export default {
    namespaced: true,
    state,
    mutations,
    actions,
};