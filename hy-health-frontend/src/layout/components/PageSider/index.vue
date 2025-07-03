<template>
  <div class="sider-wrapper">
    <el-menu
      class="el-menu-vertical-demo"
      background-color="#f8fbfd"
      text-color="#333"
      active-text-color="#007acc"
      router
      :default-active="route.path"
    >
      <template v-for="(item, index) in submenuList" :key="index">
        <!-- 一级菜单（无子菜单）-->
        <el-menu-item :index="item.path" v-if="!item.children">
          <i :class="item.icon"></i>
          <span>{{ item.title }}</span>
        </el-menu-item>

        <!-- 一级菜单（有子菜单）-->
        <el-sub-menu :index="item.path" v-else>
          <template #title>
            <i :class="item.icon"></i>
            <span>{{ item.title }}</span>
          </template>

          <!-- 二级菜单 -->
          <template v-for="(i, index) in item.children" :key="index">
            <el-menu-item :index="i.path" v-if="!i.list">
              <i :class="i.icon"></i>
              <span>{{ i.title }}</span>
            </el-menu-item>

            <!-- 二级菜单下还有三级菜单 -->
            <el-sub-menu :index="i.path" v-if="i.list">
              <template #title>
                <i :class="i.icon"></i>
                <span>{{ i.title }}</span>
              </template>
              <el-menu-item
                v-for="(j, index) in i.list"
                :key="index"
                :index="j.path"
              >
                <i :class="j.icon"></i>
                <span>{{ j.title }}</span>
              </el-menu-item>
            </el-sub-menu>
          </template>
        </el-sub-menu>
      </template>
    </el-menu>
  </div>
</template>

<script>
import { useRoute } from "vue-router";

export default {
  name: "PageSider",
  data() {
    return {
      submenuList: [],
    };
  },
  setup() {
    const route = useRoute();
    return { route };
  },
  methods: {
    handleMenuListData(data, arr) {
      data.forEach((datas) => {
        arr.push({
          path: datas.path,
          title: datas.meta.title,
          icon: "el-icon-menu",
        });
      });
      return arr;
    },
  },
  mounted() {
    const menuArray = this.$store.getters.menuList.slice(2);
    if (menuArray.length > 0 && menuArray[0].children) {
      this.submenuList = this.handleMenuListData(menuArray[0].children, []);
    } else {
      this.submenuList = [];
    }
  },
  watch: {
    "$store.getters.menuList": {
      handler(newVal) {
        const menuArray = newVal.slice(2);
        if (menuArray.length > 0 && menuArray[0].children) {
          this.submenuList = this.handleMenuListData(
            menuArray[0].children,
            []
          );
        } else {
          this.submenuList = [];
        }
      },
      immediate: true,
      deep: true,
    },
  },
};
</script>

<style lang="less" scoped>
.sider-wrapper {
  width: 200px;
  height: 100%;
  background-color: #f8fbfd;
  border-right: 1px solid #dce3e8;
  box-shadow: 1px 0 4px rgba(0, 0, 0, 0.03);
}

.el-menu {
  height: 100%;
  border-right: none;
  background-color: transparent;

  .el-menu-item,
  .el-sub-menu__title {
    font-size: 14px;
    padding-left: 20px !important;
    height: 48px;
    line-height: 48px;
    display: flex;
    align-items: center;
    transition: background-color 0.3s ease;
  }

  .el-menu-item:hover,
  .el-sub-menu__title:hover {
    background-color: #eaf6ff !important;
  }

  .el-menu-item.is-active {
    background-color: #d1ebff !important;
    color: #007acc !important;
    font-weight: 600;
    border-right: 3px solid #007acc;
  }
}

</style>
