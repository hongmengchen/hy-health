const getters = {
    token: (state) => state.app.token,
    menuList: (state) => state.app.menuList,
    companyInfo: (state) => state.companyInfoManage.companyInfo,
    cityInfo: (state) => state.cityInfoManage.cityInfo,
};
export default getters;