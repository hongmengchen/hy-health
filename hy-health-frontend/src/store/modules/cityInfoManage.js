import {
    getCityInfo,
    addCity,
    deleteCity,
    getAllCityInfo,
} from "@/api/admin/cityInfoManage";

const state = {
    cityInfo: {}, //城市管理信息
};

const mutations = {
    GET_CITY_INFO(state, payload) {
        state.cityInfo = payload;
    },
};

const actions = {
    // 城市管理信息分页，关键字查询
    getCityInfo({commit}, {pn, size, keyword}) {
        getCityInfo(pn, size, keyword).then((res) => {
            if (res) {
                commit("GET_CITY_INFO", res.data.data.cityPageInfo);
            }
        });
    },
    // 新增城市
    addCity({dispatch}, {cityNumber, size}) {
        addCity(cityNumber).then((res) => {
            // 新增之后跳转到最后一页
            if (res.data.code == 20000) {
                dispatch("getCityInfo", {pn: res.data.data.pages, size});
            }
        });
    },
    // 删除城市信息
    deleteCity({dispatch}, {cityId, pn, size, keyword}) {
        deleteCity(cityId).then(() => {
            dispatch("getCityInfo", {pn, size, keyword});
        });
    },
    // 查询所有城市信息
    getAllCityInfo({commit}) {
        getAllCityInfo().then((res) => {
            commit("GET_CITY_INFO", res.data.data.cityPageInfo);
        });
    },
};
export default {
    namespaced: true,
    state,
    mutations,
    actions,
};