import {
    judgeDeleteResult,
    judgeAddResult,
    judgeQueryResult,
    judgeModifyResult,
} from "@/utils/app";
import request from "../../utils/request";

// 查询必备材料信息api
export function getMaterialInfo(pn, size, keyword = "") {
    return request({
        url: "/materials",
        method: "GET",
        params: {
            pn,
            size,
            keyword,
        },
    }).then((res) => judgeQueryResult(res));
}

// 新增
export function addMaterial(message, title) {
    return request({
        url: "/materials",
        method: "POST",
        data: {message, title},
    }).then((res) => judgeAddResult(res));
}

// 删除
export function deleteMaterial(id) {
    return request({
        url: `/materials/${id}`,
        method: "DELETE",
        data: {
            id,
        },
    }).then((res) => {
        judgeDeleteResult(res);
    });
}

// 修改
export function modifyMaterial(id, message, title) {
    return request({
        url: `/materials/${id}`,
        method: "PUT",
        data: {
            message,
            title,
        },
    }).then((res) => {
        judgeModifyResult(res);
    });
}