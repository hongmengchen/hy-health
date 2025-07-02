import {
    getMedicalPolicyInfo,
    addMedicalPolicy,
    modifyMedicalPolicyInfo,
    deleteMedicalPolicy,
} from "@/api/admin/medicalPolicyInfoManage";

const state = {
    medicalPolicyInfo: {}, //医保政策信息
};
const mutations = {
    GET_MEDICAL_POLICY_INFO(state, payload) {
        state.medicalPolicyInfo = payload;
    },
};
const actions = {
    // 医保政策信息分页，关键字查询
    getMedicalPolicyInfo({commit}, params) {
        getMedicalPolicyInfo(params).then((res) => {
            if (res) {
                commit("GET_MEDICAL_POLICY_INFO", res.data.data.policyInfo);
            }
        });
    },
    // 新增
    addMedicalPolicy({dispatch}, {cityId, title, updateTime, message, size}) {
        addMedicalPolicy(cityId, title, updateTime, message).then((res) => {
            //   新增之后跳转到最后一页
            dispatch("getMedicalPolicyInfo", {pn: res.data.data.pages, size});
        });
    },
    //  修改医保政策
    modifyMedicalPolicyInfo(
        {dispatch},
        {id, cityId, title, updateTime, message, params}
    ) {
        modifyMedicalPolicyInfo(id, cityId, title, updateTime, message).then(() => {
            dispatch("getMedicalPolicyInfo", params);
        });
    },
    // 删除医保政策
    deleteMedicalPolicy({dispatch}, {id, params}) {
        deleteMedicalPolicy(id).then(() => {
            dispatch("getMedicalPolicyInfo", params);
        });
    },
};
export default {
    namespaced: true,
    state,
    mutations,
    actions,
};