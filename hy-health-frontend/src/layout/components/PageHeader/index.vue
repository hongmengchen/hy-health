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
@import "../../../assets/icon/icon.css";

.header-container {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 80px;
  padding: 0 24px;
  background: linear-gradient(90deg, #eaf6ff, #f5faff);
  border-bottom: 1px solid #d8e1e8;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.04);
}

.HomeLogo {
  background: url("../../../assets/HomeLogo.png") no-repeat center center;
  background-size: contain;
  height: 60px;
  width: 220px;
  cursor: pointer;
  transition: transform 0.2s;

  &:hover {
    transform: scale(1.03);
  }
}

.main-head {
  display: flex;
  align-items: center;
  gap: 20px;

  .welcome-text {
    font-size: 15px;
    color: #444;
    letter-spacing: 0.3px;

    #username {
      margin-left: 6px;
      padding: 3px 10px;
      background-color: #d8ecff;
      color: #0077cc;
      border-radius: 10px;
      font-weight: 600;
    }
  }

  .exit-area {
    display: flex;
    align-items: center;
    cursor: pointer;
    padding: 5px 10px;
    border-radius: 4px;
    transition: background-color 0.2s ease;

    &:hover {
      background-color: #e6f0f8;
    }

    .icon-tuichu {
      font-size: 18px;
      color: #666;
      margin-right: 5px;
    }

    .logout {
      font-size: 14px;
      font-weight: 500;
      color: #333;
    }
  }
}
</style>
