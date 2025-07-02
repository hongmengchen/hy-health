const getters = {
    // isCollapse: (state) => state.app.isCollapse,
    token: (state) => state.app.token,
    menuList: (state) => state.app.menuList,
    companyInfo: (state) => state.companyInfoManage.companyInfo,
    salePlaceInfo: (state) => state.saleInfoManage.salePlaceInfo,
    cityInfo: (state) => state.cityInfoManage.cityInfo,
    drugInfo: (state) => state.drugInfoManage.drugInfo,
    medicalPolicyInfo: (state) => state.medicalPolicyInfoManage.medicalPolicyInfo,
    companyPolicyInfo: (state) => state.companyPolicyInfoManage.companyPolicyInfo,
    doctorInfo: (state) => state.doctorInfoManage.doctorInfo,
    doctorLevelAndType: (state) => state.doctorInfoManage.doctorLevelAndType,
    materialInfo: (state) => state.materialInfoManage.materialInfo,
    saleAllPlaceInfo: (state) => state.saleInfoManage.saleAllPlaceInfo,
};
export default getters;