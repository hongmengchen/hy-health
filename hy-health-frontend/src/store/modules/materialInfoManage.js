import {
    getMaterialInfo,
    addMaterial,
    deleteMaterial,
    modifyMaterial,
} from "@/api/admin/materialInfoManage";

const state = {
    materialInfo: {}, //必备材料信息
};
const mutations = {
    GET_MATERIAL_INFO(state, payload) {
        state.materialInfo = payload;
    },
};
const actions = {
    // 信息分页，关键字查询
    getMaterialInfo({commit}, {pn, size, keyword}) {
        getMaterialInfo(pn, size, keyword).then((res) => {
            if (res) {
                commit("GET_MATERIAL_INFO", res.data.data.materialInfo);
            }
        });
    },
    // 新增
    addMaterial({dispatch}, {message, title, size}) {
        addMaterial(message, title).then((res) => {
            //   新增之后跳转到最后一页
            dispatch("getMaterialInfo", {pn: res.data.data.pages, size});
        });
    },
    // 删除
    deleteMaterial({dispatch}, {id, pn, size, keyword}) {
        deleteMaterial(id).then(() => {
            dispatch("getMaterialInfo", {pn, size, keyword});
        });
    },
    // 修改
    modifyMaterial({dispatch}, {id, message, title, pn, size, keyword}) {
        modifyMaterial(id, message, title).then(() => {
            dispatch("getMaterialInfo", {pn, size, keyword});
        });
    },
};
export default {
    namespaced: true,
    state,
    mutations,
    actions,
};