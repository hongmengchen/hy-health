<template>
  <el-container class="home-container">
    <!-- 顶部 header -->
    <el-header class="layout-header">
      <page-header></page-header>
    </el-header>

    <!-- 主体区域 -->
    <el-container>
      <!-- 左侧侧边栏 -->
      <el-aside width="200px" class="page-sider">
        <page-sider />
      </el-aside>

      <!-- 右侧主视图 -->
      <el-main class="main-view">
        <transition mode="out-in">
          <router-view />
        </transition>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import PageSider from "./components/PageSider/index.vue";
import PageHeader from "./components/PageHeader/index.vue";

export default {
  name: "LayoutIndex",
  components: { PageSider, PageHeader },
};
</script>

<style scoped lang="less">
.home-container {
  position: fixed;
  left: 0;
  top: 0;
  right: 0;
  bottom: 0;
  overflow: hidden;
  background-color: #e6f0fa; /* 和头部统一浅蓝背景 */
}

.layout-header {
  height: 80px;
  padding: 0;
  margin: 0;
  line-height: normal;
  box-shadow: 0 1px 8px rgba(30, 60, 120, 0.12);
}

.page-sider {
  height: 100%;
  border-right: none;
}

.main-view {
  padding: 24px;
  background-color: #fff; /* 主内容区白色 */
  overflow-y: auto;
  height: 100%;
  box-sizing: border-box;
  border-radius: 0 12px 12px 0;
  box-shadow: inset 0 0 14px #a7b9d8aa;
}

.v-leave-active {
  animation: fadeOut 0.4s linear;
}
.v-enter-active {
  animation: fadeIn 0.5s linear;
}
</style>


