const getters = {
    token: (state) => state.app.token,
    menuList: (state) => state.app.menuList,
    salePlaceInfo: (state) => state.saleInfoManage.salePlaceInfo,
    companyInfo: (state) => state.companyInfoManage.companyInfo,
    cityInfo: (state) => state.cityInfoManage.cityInfo,
    drugInfo: (state) => state.drugInfoManage.drugInfo,
    companyPolicyInfo: (state) => state.companyPolicyInfoManage.companyPolicyInfo,
    materialInfo: (state) => state.materialInfoManage.materialInfo,
};
export default getters;