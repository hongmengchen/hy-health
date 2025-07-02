import Vuex from "vuex";
import getters from "./getters";
// 通过正则表达式获取modules文件夹下的所有js文件
const modulesFiles = require.context("./modules", true, /\.js$/);
// 遍历模块文件,将所有的单个模块,汇总成符合vuex规范的modules.
const modules = modulesFiles.keys().reduce((modules, modulePath) => {
    const moduleName = modulePath.replace(/^\.\/(.*)\.\w+$/, "$1");
    const value = modulesFiles(modulePath);
    modules[moduleName] = value.default;
    return modules;
}, {});

// vue.store统一配置modules
const store = new Vuex.Store({
    modules,
    getters,
});
export default store;