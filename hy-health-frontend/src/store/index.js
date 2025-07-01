// src/store/index.js
import {createStore} from 'vuex'
import getters from './getters'

// 通过正则表达式获取 modules 文件夹下的所有 js 文件
const modulesFiles = require.context('./modules', true, /\.js$/)

// 遍历模块文件，将所有的单个模块，汇总成符合 vuex 规范的 modules
const modules = modulesFiles.keys().reduce((modules, modulePath) => {
    const moduleName = modulePath.replace(/^\.\/(.*)\.\w+$/, '$1')
    const value = modulesFiles(modulePath)
    modules[moduleName] = value.default
    return modules
}, {})

// 创建 store 实例
const store = createStore({
    modules,
    getters
})

export default store