import {
    judgeDeleteResult,
    judgeAddResult,
    judgeQueryResult,
} from "@/utils/app";
import request from "../../utils/request";

// 查询城市管理信息api
export function getCityInfo(pn, size, keyword = "") {
    return request({
        url: `/citys/${pn}/${size}`,
        method: "GET",
        params: {
            name: keyword,
        },
    }).then((res) => judgeQueryResult(res));
}

// 查询所有城市信息api
export function getAllCityInfo() {
    return request({
        url: "/citys",
        method: "GET",
    });
}

// 新增城市api
export function addCity(cityNumber) {
    return request({
        url: `/citys?cityNumber=${cityNumber}`,
        method: "POST",
    }).then((res) => judgeAddResult(res));
}

// 删除城市api
export function deleteCity(cityId) {
    return request({
        url: `/citys/${cityId}`,
        method: "DELETE",
    }).then((res) => {
        judgeDeleteResult(res);
    });
}