import {
    judgeDeleteResult,
    judgeAddResult,
    judgeQueryResult,
    judgeModifyResult,
} from "@/utils/app";
import request from "../../utils/request";
// import Qs from 'qs'

// 查询药品信息api
export function getDrugInfo(pn, size, keyword = "") {
    return request({
        url: `/drugs/${pn}/${size}`,
        method: "GET",
        params: {
            name: keyword,
        },
    }).then((res) => judgeQueryResult(res));
}

// 新增药品api
export function addDrug(drugName, drugInfo, drugEffect, drugImg, saleIds, drugPublisher) {
    return request({
        url: "/drugs",
        method: "POST",
        data: {
            drugName,
            drugInfo,
            drugEffect,
            drugImg,
            saleIds, //售卖该药品的药店id（数组）
            drugPublisher
        },
    }).then((res) => judgeAddResult(res));
}

// 删除公司api
export function deleteDrug(drugId) {
    return request({
        url: `/drugs/${drugId}`,
        method: "DELETE",
    }).then((res) => {
        judgeDeleteResult(res);
    });
}

// 修改公司api
export function modifyDrugInfo(
    drugId,
    drugName,
    drugInfo,
    drugEffect,
    drugImg,
    saleIds
) {
    return request({
        url: `/drugs/${drugId}`,
        method: "PUT",
        data: {
            drugName,
            drugInfo,
            drugEffect,
            drugImg,
            saleIds,
        },
    }).then((res) => {
        judgeModifyResult(res);
    });
}