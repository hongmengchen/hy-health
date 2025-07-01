<template>
  <div class="LoginBackground">
    <div class="LoginForm">
      <div class="big-contain">
        <el-form :model="loginForm" ref="loginForm" status-icon>
          <div class="bform">
            <el-form-item prop="username" :rules="loginRules.usernameRules" class="NameNotNull">
              <div class="UserName">
                <img src="../assets/username.png" class="Logo">
                <input
                    class="Input"
                    type="text"
                    v-model="loginForm.username"
                    autocomplete="off"
                    placeholder="用户名"
                />
              </div>
            </el-form-item>
            <el-form-item prop="password" :rules="loginRules.passwordRules" class="PasswordNotNull">
              <div class="PassWord">
                <img src="../assets/password.png" class="Logo">
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
              <button class="LoginBtn" @click="handleLogin('loginForm')"/>
            </el-form-item>
          </div>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import {loginRules} from "@/utils/validator";

export default {
  name: "loginVue",

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
    // 登录事件
    handleLogin(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$store
              .dispatch("app/login", this.loginForm)
              .then(() => {
                this.$store.dispatch("app/setMenuList");
                setTimeout(() => {
                  // 使用 push 替代 replace
                  this.$router.push("/home");
                }, 1200);
              })
              .catch(() => {
                this.$message.error("账号或密码错误");
              });
        } else {
          this.$notify.error({
            title: " 错误",
            message: " 请输入正确的用户名密码",
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
  background: url("../assets/LoginBackground.jpg") no-repeat;
  background-size: cover;
  width: 100%;
  height: 100%;
  overflow: hidden;

}

.LoginForm {
  background: url("../assets/Login.png") no-repeat;
  background-size: 800px, 599px;
  width: 1000px;
  height: 800px;
  margin-left: 555px;
  margin-top: 100px;
  overflow: hidden;
}

.UserName {
  overflow: hidden;
  border: 2px solid #2abeb2;
  border-radius: 20px;
  margin-top: 270px;
  margin-left: -60px;
  height: 70px;
  width: 530px;
}

.PassWord {
  overflow: hidden;
  border: 2px solid #2abeb2;
  border-radius: 20px;
  margin-top: 20px;
  margin-left: -60px;
  height: 70px;
  width: 530px;
}

.Input {
  overflow: hidden;
  margin-left: 20px;
  height: 80px;
  font-size: 35px;
  color: #2abeb2;

}

.Logo {
  overflow: hidden;
  margin-left: 15px;
  margin-bottom: 5px;
}

.LoginBtn {
  overflow: hidden;
  background: url("../assets/LoginButton.png");
  background-size: 100%;
  width: 220px;
  height: 65px;
  margin-left: 290px;
  margin-top: 10px;
}

.NameNotNull {
  margin-left: 200px;

}

.PasswordNotNull {
  margin-left: 200px;
}

input::-webkit-input-placeholder {
  color: #2abeb2;
}

input::-ms-input-placeholder {
  color: #2abeb2;
}

</style>