import {
    getSalePlaceInfo,
    addSalePlace,
    deleteSalePlace,
    modifySalePlaceInfo,
    getAllSalePlaceInfo,
} from "@/api/admin/saleInfoManage";

const state = {
    salePlaceInfo: {}, //销售地点信息
    saleAllPlaceInfo: {},
};
const mutations = {
    GET_SALE_PLACE_INFO(state, payload) {
        state.salePlaceInfo = payload;
    },
    GET_ALL_SALE_PLACE_INFO(state, payload) {
        state.saleAllPlaceInfo = payload;
    },
};
const actions = {
    // 销售地点信息分页，关键字查询
    getSalePlaceInfo({commit}, {pn, size, keyword}) {
        getSalePlaceInfo(pn, size, keyword).then((res) => {
            if (res) {
                commit("GET_SALE_PLACE_INFO", res.data.data.salePageInfo);
            }
        });
    },
    // 新增销售地点
    addSalePlace({dispatch}, {saleName, salePhone, lng, lat, address, size}) {
        addSalePlace(saleName, salePhone, lng, lat, address).then((res) => {
            //   新增之后跳转到最后一页
            dispatch("getSalePlaceInfo", {pn: res.data.data.pages, size});
            dispatch("getAllSalePlaceInfo");
        });
    },
    // 删除销售地点信息
    deleteSalePlace({dispatch}, {saleId, pn, size, keyword}) {
        deleteSalePlace(saleId).then(() => {
            dispatch("getSalePlaceInfo", {pn, size, keyword});
            dispatch("getAllSalePlaceInfo");
        });
    },
    // 修改销售地点信息
    modifySalePlaceInfo(
        {dispatch},
        {saleId, saleName, salePhone, pn, size, keyword}
    ) {
        modifySalePlaceInfo(saleId, saleName, salePhone).then(() => {
            dispatch("getSalePlaceInfo", {pn, size, keyword});
            dispatch("getAllSalePlaceInfo");
        });
    },
    // 获取所有销售地点
    getAllSalePlaceInfo({commit}) {
        getAllSalePlaceInfo().then((res) => {
            commit("GET_ALL_SALE_PLACE_INFO", res.data.data.salePageInfo);
        });
    },
};
export default {
    namespaced: true,
    state,
    mutations,
    actions,
};