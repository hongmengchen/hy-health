<template>
  <div class="LoginBackground">
    <div class="LoginForm">
      <div class="big-contain">
        <!-- 平台 LOGO -->
        <img src="@/assets/HomeLogo.png" class="PlatformLogo" alt="平台 Logo" />

        <el-form :model="loginForm" ref="loginForm" status-icon>
          <div class="bform">
            <el-form-item prop="username" :rules="loginRules.usernameRules">
              <div class="InputWrapper">
                <el-icon class="icon"><User /></el-icon>
                <input
                  class="Input"
                  type="text"
                  v-model="loginForm.username"
                  autocomplete="off"
                  placeholder="用户名"
                />
              </div>
            </el-form-item>

            <el-form-item prop="password" :rules="loginRules.passwordRules">
              <div class="InputWrapper">
                <el-icon class="icon"><Lock /></el-icon>
                <input
                  class="Input"
                  type="password"
                  v-model="loginForm.password"
                  autocomplete="off"
                  placeholder="密码"
                  @keydown.enter="handleLogin('loginForm')"
                />
              </div>
            </el-form-item>

            <el-form-item>
              <button type="button" class="LoginBtn" @click="handleLogin('loginForm')">
                登录
              </button>
            </el-form-item>
          </div>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import { User, Lock } from '@element-plus/icons-vue'
import { loginRules } from "@/utils/validator";

export default {
  name: "loginVue",
  components: {
    User,
    Lock,
  },
  data() {
    return {
      loginForm: {
        username: "",
        password: "",
      },
      loginRules,
    };
  },
  methods: {
    handleLogin(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$store
            .dispatch("app/login", this.loginForm)
            .then(() => {
              this.$store.dispatch("app/setMenuList");
              this.$router.replace("/");
            })
            .catch(() => {
              this.$message.error("账号或密码错误");
            });
        } else {
          this.$notify.error({
            title: "错误",
            message: "请输入正确的用户名密码",
          });
          return false;
        }
      });
    },
  },
};
</script>

<style scoped>
.LoginBackground {
  background: url("../assets/loginBackground.png") no-repeat center center;
  background-size: cover;
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

.LoginForm {
  width: 500px;
  padding: 40px 40px 40px;
  background-color: rgba(255, 255, 255, 0.92);
  border-radius: 16px;
  box-shadow: 0 0 30px rgba(0, 0, 0, 0.15);
  display: flex;
  flex-direction: column;
  align-items: center;
}

.PlatformLogo {
  max-width: 450px;     /* 限制最大宽度 */
  width: 100%;          /* 宽度不会撑开容器 */
  height: auto;
  margin: 0 auto 30px;
  display: block;
}


.InputWrapper {
  border: 2px solid #3a69d8;
  border-radius: 12px;
  display: flex;
  align-items: center;
  height: 50px;
  width: 100%;
  padding: 0 16px;
  box-sizing: border-box;
  margin-bottom: 20px;
}

.icon {
  font-size: 24px;
  color: #3a69d8;
  margin-right: 10px;
}

.Input {
  flex: 1;
  height: 40px;
  font-size: 16px;
  color: #3a69d8;
  border: none;
  outline: none;
  background-color: transparent;
}

.Input::placeholder {
  color: #3a69d8;
}

.LoginBtn {
  background: linear-gradient(90deg, #3a8ed8, #4aa7f3); /* 蓝色渐变 */
  color: white;
  font-size: 16px;
  font-weight: bold;
  border: none;
  border-radius: 8px;
  width: 180px;
  height: 50px;
  margin: 20px auto 0;
  display: block;
  cursor: pointer;
  transition: background 0.3s ease;
}

.LoginBtn:hover {
  background: linear-gradient(90deg, #2f80ed, #56ccf2); /* 悬浮更亮蓝 */
}

</style>
