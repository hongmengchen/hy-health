import {
    judgeDeleteResult,
    judgeAddResult,
    judgeQueryResult,
    judgeModifyResult,
} from "@/utils/app";
import request from "../../utils/request";

// 查询医药公司政策管理信息api
export function getCompanyPolicyInfo(pn, size, keyword = "") {
    return request({
        url: `/company_policys`,
        method: "GET",
        params: {
            pn,
            size,
            keyword,
        },
    }).then((res) => judgeQueryResult(res));
}

// 新增
export function addCompanyPolicy(companyId, message, title) {
    return request({
        url: "/company_policys",
        method: "POST",
        data: {
            companyId,
            message,
            title,
        },
    }).then((res) => judgeAddResult(res));
}

// 删除
export function deleteCompanyPolicy(id) {
    return request({
        url: `/company_policys/${id}`,
        method: "DELETE",
    }).then((res) => {
        judgeDeleteResult(res);
    });
}

// 修改
export function modifyCompanyPolicyInfo(id, companyId, title, message) {
    return request({
        url: `/company_policys/${id}`,
        method: "PUT",
        data: {
            companyId,
            title,
            message,
        },
    }).then((res) => {
        judgeModifyResult(res);
    });
}