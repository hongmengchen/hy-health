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
  width: 220px;
  height: 100%;
  background-color: #c8d9f5; /* 比头部浅蓝稍深 */
  border-right: 1px solid #9ab3e6; /* 细边框，蓝色调 */
  box-shadow: inset -2px 0 6px rgba(0, 0, 0, 0.06);
  user-select: none;
}

.el-menu {
  height: 100%;
  border-right: none;
  background-color: transparent;
  color: #1b2a5f; /* 深蓝文字 */

  .el-menu-item,
  .el-sub-menu__title {
    font-size: 15px;
    padding-left: 28px !important;
    height: 50px;
    line-height: 50px;
    display: flex;
    align-items: center;
    transition: background-color 0.25s ease, color 0.25s ease;
    border-radius: 8px;
  }

  .el-menu-item:hover,
  .el-sub-menu__title:hover {
    background-color: #a3bffa !important; /* 亮蓝悬停 */
    color: #0f1c3d !important; /* 深蓝字体悬停 */
  }

  .el-menu-item.is-active {
    background-color: #759de8 !important; /* 活跃条目更深蓝 */
    color: #0a1145 !important;
    font-weight: 700;
    border-right: 5px solid #2550b3;
    box-shadow: 0 0 12px #2550b3aa;
  }

  i {
    margin-right: 14px;
    font-size: 18px;
    color: #1b2a5f;
  }

  .el-sub-menu__title i {
    color: #1b2a5f;
  }
}
</style>


