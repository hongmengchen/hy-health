const getters = {
    token: (state) => state.app.token,
    menuList: (state) => state.app.menuList,
    companyInfo: (state) => state.companyInfoManage.companyInfo,
};
export default getters;