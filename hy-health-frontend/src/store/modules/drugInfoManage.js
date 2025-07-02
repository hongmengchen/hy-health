import {
    getDrugInfo,
    addDrug,
    deleteDrug,
    modifyDrugInfo,
} from "@/api/admin/drugInfoManage";

const state = {
    drugInfo: {}, //公司信息
};
const mutations = {
    GET_DRUG_INFO(state, payload) {
        state.drugInfo = payload;
    },
};
const actions = {
    // 药品信息分页，关键字查询
    getDrugInfo({commit}, {pn, size, keyword}) {
        getDrugInfo(pn, size, keyword).then((res) => {
            if (res) {
                commit("GET_DRUG_INFO", res.data.data.drugPageInfo);
            }
        });
    },
    // 新增药品
    addDrug(
        {dispatch},
        {drugName, drugInfo, drugEffect, drugImg, saleIds, size, drugPublisher}
    ) {
        addDrug(drugName, drugInfo, drugEffect, drugImg, saleIds, drugPublisher).then((res) => {
            //   新增之后跳转到最后一页
            dispatch("getDrugInfo", {pn: res.data.data.pages, size});
        });
    },
    // 删除药品信息
    deleteDrug({dispatch}, {drugId, pn, size, keyword}) {
        deleteDrug(drugId).then(() => {
            dispatch("getDrugInfo", {pn, size, keyword});
        });
    },
    // 修改药品信息
    modifyDrugInfo(
        {dispatch},
        {
            drugId,
            drugName,
            drugInfo,
            drugEffect,
            drugImg,
            saleIds,
            pn,
            size,
            keyword,
        }
    ) {
        modifyDrugInfo(
            drugId,
            drugName,
            drugInfo,
            drugEffect,
            drugImg,
            saleIds
        ).then(() => {
            dispatch("getDrugInfo", {pn, size, keyword});
        });
    },
};
export default {
    namespaced: true,
    state,
    mutations,
    actions,
};