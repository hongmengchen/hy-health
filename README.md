# 智慧数字医疗应用系统

## 1. 项目概述

本项目是一个基于 **Spring Boot** 后端框架和 **Vue.js（3.x）** 前端框架构建的智慧数字医疗应用系统。系统旨在实现对医生信息、药品销售、医药公司政策、必备材料等模块的高效管理，适用于医院管理员和医生角色，提升医疗服务效率和质量。

### 1.1 功能模块

| 模块名称 | 描述 |
|----------|------|
| 用户管理 | 管理用户权限、登录、角色分配等 |
| 医师管理 | 添加、编辑、删除医师信息 |
| 医药公司管理 | 维护医药公司及其相关政策信息 |
| 药品管理 | 药品信息录入、查询及库存管理 |
| 材料管理 | 必备医疗材料的管理 |
| 销售地点管理 | 管理药品销售点信息 |
| 城市信息管理 | 城市数据维护，支持区域划分 |

### 1.2 角色与权限

| 角色 | 权限描述 |
|------|-----------|
| 管理员 | 可操作所有功能模块，包括增删改查 |
| 医生 | 仅可查看相关信息，如药品、材料、政策等 |

---

## 2. 技术栈

### 后端
- 编程语言：Java 1.8
- 框架：Spring Boot 3.x
- 数据库：MySQL 8.x
- ORM 框架：MyBatis Plus
- 安全框架：Spring Security + JWT
- 构建工具：Maven 3.x
- 开发工具：IntelliJ IDEA 2025

### 前端
- 框架：Vue.js 3.x
- 状态管理：Vuex 4.x
- 路由管理：Vue Router 4.x
- UI 库：Element Plus
- 构建工具：Webpack / Vite
- 开发工具：WebStorm 2025
- HTTP 请求库：Axios
- 样式处理：SCSS / PostCSS

---

## 3. 开发环境配置

### 后端开发环境

#### 3.1 JDK 配置
- 下载并安装 [JDK 1.8](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)
- 配置环境变量：
  ```bash
  JAVA_HOME=C:\Program Files\Java\jdk1.8.0_291
  PATH=%JAVA_HOME%\bin;%PATH%
  ```


#### 3.2 Maven 配置
- 下载并解压 [Maven 3.x](https://maven.apache.org/download.cgi)
- 配置环境变量：
  ```bash
  MAVEN_HOME=C:\Program Files\Apache\maven-3.8.6
  PATH=%MAVEN_HOME%\bin;%PATH%
  ```

- 修改 `settings.xml` 设置本地仓库路径和镜像源（如阿里云）

#### 3.3 IntelliJ IDEA 配置
- 打开项目目录 `hy-health-backend`
- 自动识别为 Maven 项目，等待依赖下载完成
- 运行主类 `HyHealthBackendApplication.java` 启动后端服务

#### 3.4 数据库配置

- 创建数据库 `hy_health`
- 导入 SQL 文件 `sql/hy-health.sql`
- 修改 `application.yml` 中的数据库连接信息：
  ```yaml
  spring:
    datasource:
      url: jdbc:mysql://localhost:3306/hy_health?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
      username: root
      password: your_password
  ```


---

### 前端开发环境

#### 3.5 Node.js 配置
- 下载并安装 [Node.js 20.x](https://nodejs.org/en/)
- 验证安装：
  ```bash
  node -v
  npm -v
  ```


#### 3.6 Vue 项目初始化
- 进入前端目录：
  ```bash
  cd hy-health-frontend
  ```

- 安装依赖：
  ```bash
  npm install
  ```

- 启动开发服务器：
  ```bash
  npm run serve
  ```


#### 3.7 WebStorm 配置
- 打开项目目录 `hy-health-frontend`
- 配置 JavaScript 解析器为 ECMAScript 6+
- 使用内置终端运行 `npm run serve` 启动项目

---

## 4. 项目运行指南

### 后端启动流程
1. 确保 MySQL 已启动
2. 在 IntelliJ IDEA 中运行 `HyHealthBackendApplication.java`

### 前端启动流程
1. 在项目根目录下运行：
   ```bash
   npm run serve
   ```

2. 浏览器访问：`http://localhost:9092`

---

## 5. 项目部署

### 后端部署
1. 打包项目：
   ```bash
   mvn clean package
   ```

2. 上传生成的 `jar` 文件到服务器
3. 启动服务：
   ```bash
   java -jar hy-health-backend.jar
   ```


### 前端部署
1. 打包项目：
   ```bash
   npm run build
   ```

2. 将 `dist` 目录上传至 Nginx 或任意静态资源服务器
3. 配置反向代理（如需跨域解决）：
   ```nginx
   location /api {
       proxy_pass http://backend_server_ip:8080;
   }
   ```


---

## 6. 注意事项
- 前后端交互使用 RESTful API，所有请求需携带 JWT Token（登录后获取）
- 前端默认使用 `.env.development` 配置开发环境，生产环境请修改 `.env.production`
- 所有敏感信息（如数据库密码）建议通过环境变量配置，避免硬编码