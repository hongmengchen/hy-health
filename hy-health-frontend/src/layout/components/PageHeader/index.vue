<template>
  <div class="header-container">
    <div class="HomeLogo" @click="goHome" title="返回首页"></div>
    <div class="main-head">
      <div class="welcome-text">
        欢迎来到慧医数字医疗应用系统，当前用户：
        <span id="username">{{ userName }}</span>
      </div>
      <div class="exit-area" @click="handleLogout" title="退出登录">
        <i class="iconfont icon-tuichu"></i>
        <span class="logout">退出</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "PageHeader",
  methods: {
    handleLogout() {
      localStorage.removeItem("token");
      localStorage.removeItem("userInfo");
      this.$message.warning("已退出登录");
      this.$router.replace("/user/login");
      this.$router.go(0);
    },
    goHome() {
      this.$router.push("/");
    },
  },
  computed: {
    userName() {
      return JSON.parse(localStorage.getItem("userInfo")).realname;
    },
  },
};
</script>

<style lang="less" scoped>
.header-container {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 80px;
  padding: 0 32px;
  background-color: #e6f0fa; /* 浅蓝背景 */
  box-shadow: 0 1px 6px rgba(30, 60, 120, 0.1);
  color: #1b2a5f; /* 深蓝文字 */
  user-select: none;
}

.HomeLogo {
  background: url("../../../assets/HomeLogo.png") no-repeat center center;
  background-size: contain;
  height: 90px;
  width: 330px;
  cursor: pointer;
  transition: transform 0.3s ease;

  &:hover {
    transform: scale(1.05);
  }
}

.main-head {
  display: flex;
  align-items: center;
  gap: 28px;
  font-weight: 600;

  .welcome-text {
    font-size: 16px;
    color: #1b2a5f;

    #username {
      margin-left: 8px;
      padding: 4px 14px;
      background-color: #a3bffa; /* 柔和亮蓝 */
      color: #0f1c3d; /* 深蓝 */
      border-radius: 14px;
      font-weight: 700;
      box-shadow: 0 0 6px #a3bffaaa;
      user-select: text;
    }
  }

  .exit-area {
    display: flex;
    align-items: center;
    cursor: pointer;
    padding: 6px 16px;
    border-radius: 20px;
    background-color: #e74c3c; // 鲜红色
    color: #fff;
    font-size: 14px;
    font-weight: 600;
    transition: background-color 0.3s ease, box-shadow 0.3s ease;

    &:hover {
      background-color: #c0392b; // 深红悬停
      box-shadow: 0 0 12px #c0392bcc;
    }

    .icon-tuichu {
      font-size: 20px;
      margin-right: 8px;
    }
  }
}
</style>



