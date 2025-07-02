import {
    judgeDeleteResult,
    judgeAddResult,
    judgeQueryResult,
    judgeModifyResult,
} from "@/utils/app";
import request from "../../utils/request";
// import Qs from 'qs'

// 查询销售地点信息api
export function getSalePlaceInfo(pn, size, keyword = "") {
    return request({
        url: `/sales/${pn}/${size}`,
        method: "GET",
        params: {
            name: keyword,
        },
    }).then((res) => judgeQueryResult(res));
}
// 查询所有销售地点信息
export function getAllSalePlaceInfo() {
    return request({
        url: "/sales",
        method: "GET",
    });
}
// 新增销售地点api
export function addSalePlace(saleName, salePhone) {
    return request({
        url: "/sales",
        method: "POST",
        data: {
            saleName,
            salePhone,
        },
    }).then((res) => judgeAddResult(res));
}
// 删除销售地点api
export function deleteSalePlace(saleId) {
    return request({
        url: `/sales/${saleId}`,
        method: "DELETE",
    }).then((res) => {
        judgeDeleteResult(res);
    });
}
// 修改销售地点api
export function modifySalePlaceInfo(saleId, saleName, salePhone) {
    return request({
        url: `/sales/${saleId}`,
        method: "PUT",
        data: {
            saleName,
            salePhone,
        },
    }).then((res) => {
        judgeModifyResult(res);
    });
}