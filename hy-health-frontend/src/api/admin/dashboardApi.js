import request from "../../utils/request";

// 查询所有城市信息api
export function getAllDashboardInfo() {
    return request({
        url: "/dashboard",
        method: "GET",
    });
}