<template>
  <el-container class="home-container">
    <!-- 侧边栏 -->
    <el-header style="height: 100px">
      <page-header></page-header>
    </el-header>
    <el-container>
      <el-aside width="auto">
        <page-sider class="page-sider"></page-sider>
      </el-aside>
      <el-main class="main-view">
        <transition mode="out-in">
          <router-view></router-view>
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
  components: {PageSider, PageHeader},
};
</script>

<style scoped lang="less">
.home-container {
  position: fixed;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
}

.page-sider {
  height: 100%;
}

.el-header {
  padding: 0;
  margin: 0;
  /*line-height: 100px;*/
}

.main-view {
  padding: 0;
}

.v-leave-active {
  animation: fadeOut .4s linear;
}

.v-enter-active {
  animation: fadeIn .5s linear;
}
</style>