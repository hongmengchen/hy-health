import {ElMessage} from "element-plus";

// 判断删除结果
export function judgeDeleteResult(res) {
    if (res.data.code == 20000) {
        ElMessage({
            type: "success",
            message: "删除成功!",
        });
    } else {
        ElMessage({
            type: "error",
            message: "删除失败!",
        });
    }
    return res;
}

// 判断新增结果
export function judgeAddResult(res) {
    if (res.data.code == 20000) {
        ElMessage({
            type: "success",
            message: "新增成功!",
        });
    } else if (res.data.code == 10004) {
        ElMessage({
            type: "error",
            message: "城市已存在!请重新选择",
        });
    } else if (res.data.code == 10001) {
        ElMessage({
            type: "error",
            message: "该手机号已被注册",
        });
    } else {
        ElMessage({
            type: "error",
            message: "新增失败",
        });
    }
    return res;
}

// 判断查询结果
export function judgeQueryResult(res) {
    if (res.data.code == 20000) {
        return res;
    } else {
        ElMessage({
            type: "error",
            message: "加载数据失败!",
        });
        return false;
    }
}

// 判断修改结果
export function judgeModifyResult(res) {
    if (res.data.code == 20000) {
        ElMessage({
            type: "success",
            message: "修改成功!",
        });
    } else if (res.data.code == 10001) {
        ElMessage({
            type: "error",
            message: "该手机号已被注册",
        });
    } else {
        ElMessage({
            type: "error",
            message: "修改失败!",
        });
    }
    return res;
}