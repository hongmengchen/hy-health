<template>
  <div>
    <el-menu
      class="el-menu-vertical-demo"
      background-color="#2abeb2"
      text-color="#000000"
      active-text-color="#ffffff"
      router
      :default-active="$route.path"
    >
      <div class="MenuBackground">
        <!-- 使用 Vue 3 原生 <template> 替代 vue-fragment -->
        <template v-for="(item, index) in submenuList" :key="index">
          <!-- 一级菜单（没有任何子级菜单）-->
          <el-menu-item :index="item.path" v-if="!item.children">
            <i :class="item.icon"></i>
            <span>{{ item.title }}</span>
          </el-menu-item>
          <!-- 一级菜单（有子级菜单）-->
          <el-submenu :index="item.path" v-else>
            <template v-slot:title>
              <i :class="item.icon"></i>
              <span>{{ item.title }}</span>
            </template>
            <!-- 遍历二级菜单容器 -->
            <template v-for="(i, index) in item.children" :key="index">
              <!-- 判断二级菜单（没有三级菜单）-->
              <el-menu-item :index="i.path" v-if="!i.list">
                <i :class="i.icon"></i>
                <span>{{ i.title }}</span>
              </el-menu-item>
              <!-- 判断二级菜单（有三级菜单）-->
              <el-submenu :index="i.path" v-if="i.list">
                <template v-slot:title>
                  <i :class="i.icon"></i>
                  <span>{{ i.title }}</span>
                </template>
                <el-menu-item
                  :index="j.path"
                  v-for="(j, index) in i.list"
                  :key="index"
                >
                  <i :class="j.icon"></i>
                  <span>{{ j.title }}</span>
                </el-menu-item>
              </el-submenu>
            </template>
          </el-submenu>
        </template>
      </div>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: "PageSider",
  data() {
    return {
      submenuList: []
    };
  },
  mounted() {
    // Vue 3 选项式 API 的 mounted 生命周期
    let array = this.$store.getters.menuList.slice(2)[0]?.children || [];
    this.submenuList = this.handleMenuListData(array, []);
  },
  methods: {
    handleMenuListData(data, arr) {
      data.forEach((datas) => {
        arr.push({
          path: datas.path,
          title: datas.meta.title,
          icon: "el-icon-menu"
        });
      });
      return arr;
    }
  }
};
</script>

<style lang="less" scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}

.medicine_system_title {
  width: 100%;
  color: #fff;
  font-size: 20px;
  height: 145px;
  line-height: 145px;
  text-align: center;
  background-color: #233646;
  cursor: default;
}

.el-menu {
  height: 100%;
  border-right: 0;
}

.MenuBackground {
  background: url("../../../assets/MenuBackGround.jpg");
  background-size: 120%;
  height: 844px;
}
</style>