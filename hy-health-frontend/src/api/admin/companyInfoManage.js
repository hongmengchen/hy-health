import {
    judgeDeleteResult,
    judgeAddResult,
    judgeQueryResult,
    judgeModifyResult,
} from "@/utils/app";
import request from "../../utils/request";
// import Qs from 'qs'

// 查询公司信息api
export function getCompanyInfo(pn, size, keyword = "") {
    return request({
        url: `/companys/${pn}/${size}`,
        method: "GET",
        params: {
            name: keyword,
        },
    }).then((res) => judgeQueryResult(res));
}

// 查询所有公司信息api
export function getAllCompanyInfo() {
    return request({
        url: "/companys",
        method: "GET",
    });
}

// 新增公司api
export function addCompany(companyName, companyPhone) {
    return request({
        url: "/companys",
        method: "POST",
        data: {
            companyName,
            companyPhone,
        },
    }).then((res) => judgeAddResult(res));
}

// 删除公司api
export function deleteCompany(companyId) {
    return request({
        url: `/companys/${companyId}`,
        method: "DELETE",
    }).then((res) => {
        judgeDeleteResult(res);
    });
}

// 修改公司api
export function modifyCompanyInfo(companyId, companyName, companyPhone) {
    return request({
        url: `/companys/${companyId}`,
        method: "PUT",
        data: {
            companyName,
            companyPhone,
        },
    }).then((res) => {
        judgeModifyResult(res);
    });
}