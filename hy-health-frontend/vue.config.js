const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
  publicPath: './',  // 静态资源路径

  devServer: {
    host: "0.0.0.0",  // 监听地址
    port: 9092, // 代理端口
  },

  transpileDependencies: true  // 忽略编译
})
