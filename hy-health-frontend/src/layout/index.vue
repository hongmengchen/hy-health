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
  background-color: #f9fbfd;
}

.layout-header {
  height: 80px;
  padding: 0;
  margin: 0;
  line-height: normal;
}

.page-sider {
  height: 100%;
  border-right: 1px solid #e0e0e0;
}

.main-view {
  padding: 16px;
  background-color: #ffffff;
  overflow-y: auto;
  height: 100%;
  box-sizing: border-box;
}

.v-leave-active {
  animation: fadeOut 0.4s linear;
}
.v-enter-active {
  animation: fadeIn 0.5s linear;
}

</style>
