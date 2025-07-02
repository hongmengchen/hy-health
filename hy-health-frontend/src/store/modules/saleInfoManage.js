import {
    getSalePlaceInfo,
    addSalePlace,
    deleteSalePlace,
    modifySalePlaceInfo,
    getAllSalePlaceInfo,
} from "@/api/admin/saleInfoManage";

const state = {
    salePlaceInfo: {}, //销售地点信息
};
const mutations = {
    GET_SALE_PLACE_INFO(state, payload) {
        state.salePlaceInfo = payload;
    },
};
const actions = {
    // 销售地点信息分页，关键字查询
    getSalePlaceInfo({ commit }, { pn, size, keyword }) {
        getSalePlaceInfo(pn, size, keyword).then((res) => {
            if (res) {
                commit("GET_SALE_PLACE_INFO", res.data.data.salePageInfo);
            }
        });
    },
    // 新增销售地点
    addSalePlace({ dispatch }, { saleName, salePhone, size }) {
        addSalePlace(saleName, salePhone).then((res) => {
            //   新增之后跳转到最后一页
            dispatch("getSalePlaceInfo", { pn: res.data.data.pages, size });
        });
    },
    // 删除销售地点信息
    deleteSalePlace({ dispatch }, { saleId, pn, size, keyword }) {
        deleteSalePlace(saleId).then(() => {
            dispatch("getSalePlaceInfo", { pn, size, keyword });
        });
    },
    // 修改销售地点信息
    modifySalePlaceInfo(
        { dispatch },
        { saleId, saleName, salePhone, pn, size, keyword }
    ) {
        modifySalePlaceInfo(saleId, saleName, salePhone).then(() => {
            dispatch("getSalePlaceInfo", { pn, size, keyword });
        });
    },
    // 获取所有销售地点
    getAllSalePlaceInfo({ commit }) {
        getAllSalePlaceInfo().then((res) => {
            commit("GET_SALE_PLACE_INFO", res.data.data.salePageInfo);
        });
    },
};
export default {
    namespaced: true,
    state,
    mutations,
    actions,
};