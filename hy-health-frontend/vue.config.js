const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  // 开发、生产环境均使用绝对路径
  publicPath: process.env.NODE_ENV === 'production'
      ? '/base/'    // 与 Spring Boot 的 context-path 保持一致
      : '/',

  devServer: {
    host: "0.0.0.0",
    port: 9092,
    historyApiFallback: {
      rewrites: [
        { from: /^\/base/, to: '/index.html' }
      ]
    }
  },

  transpileDependencies: true
})
