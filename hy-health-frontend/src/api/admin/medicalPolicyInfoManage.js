import {
    judgeDeleteResult,
    judgeAddResult,
    judgeQueryResult,
    judgeModifyResult,
} from "@/utils/app";
import request from "../../utils/request";
// import Qs from 'qs'

// 查询医保政策信息api
export function getMedicalPolicyInfo(params) {
    return request({
        url: `/medical_policys`,
        method: "GET",
        params,
    }).then((res) => judgeQueryResult(res));
}

// 新增医保政策api
export function addMedicalPolicy(cityId, title, updateTime, message) {
    return request({
        url: "/medical_policys",
        method: "POST",
        data: {
            cityId,
            title,
            updateTime,
            message,
        },
    }).then((res) => judgeAddResult(res));
}

// 删除医保政策api
export function deleteMedicalPolicy(id) {
    return request({
        url: `/medical_policys/${id}`,
        method: "DELETE",
        data: {
            id,
        },
    }).then((res) => {
        judgeDeleteResult(res);
    });
}

// 修改医保政策api
export function modifyMedicalPolicyInfo(
    id,
    cityId,
    title,
    updateTime,
    message
) {
    return request({
        url: `/medical_policys/${id}`,
        method: "PUT",
        data: {
            cityId,
            title,
            updateTime,
            message,
        },
    }).then((res) => {
        judgeModifyResult(res);
    });
}