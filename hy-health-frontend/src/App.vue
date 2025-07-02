<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'app',  // 默认为文件名
  mounted() {
    // 只要刷新页面，就会重新加载路由树，保证了路由不会丢失数据
    if (localStorage.getItem('userInfo')) {
      this.$store.dispatch('app/setMenuList')
    }
  }
}
</script>