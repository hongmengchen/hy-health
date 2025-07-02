import {
    judgeDeleteResult,
    judgeAddResult,
    judgeQueryResult,
    judgeModifyResult,
    judgeResetResult,
} from "@/utils/app";
import request from "../../utils/request";

// 查询医生管理信息api
export function getDoctorInfo(pn, size, keyword = "") {
    return request({
        url: "/doctors",
        method: "GET",
        params: {
            pn,
            size,
            keyword,
        },
    }).then((res) => judgeQueryResult(res));
}

// 查询医生级别及诊治部位
export function getDoctorLevelAndType() {
    return request({
        url: "/doctors/info",
        method: "GET",
    });
}

// 新增
export function addDoctor(age, levelId, name, phoneNumber, pwd, sex, typeId) {
    return request({
        url: "/doctors",
        method: "POST",
        data: {age, levelId, name, phoneNumber, pwd, sex, typeId},
    }).then((res) => judgeAddResult(res));
}

// 删除api
export function deleteDoctor(id) {
    return request({
        url: `/doctors/${id}`,
        method: "DELETE",
    }).then((res) => {
        judgeDeleteResult(res);
    });
}

// // 修改
export function modifyDoctor(
    accountId,
    age,
    levelId,
    name,
    phoneNumber,
    pwd,
    sex,
    typeId,
    id
) {
    return request({
        url: `/doctors/${id}`,
        method: "PUT",
        data: {
            accountId,
            age,
            levelId,
            name,
            phoneNumber,
            pwd,
            sex,
            typeId,
        },
    }).then((res) => judgeModifyResult(res));
}

// 重置医生密码
export function resetPassword(id) {
    return request({
        url: `/doctors/reset/${id}`,
        method: "PUT",
    }).then((res) => {
        judgeResetResult(res);
    });
}