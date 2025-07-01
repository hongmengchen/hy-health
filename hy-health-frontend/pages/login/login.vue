<template>
  <view class="LoginBackground">
    <view class="LoginForm">
      <view class="big-contain">
        <form @submit="handleLogin">
          <view class="bform">
            <view class="form-item NameNotNull">
              <view class="UserName">
                <image src="/static/username.png" class="Logo"></image>
                <input
                  class="Input"
                  type="text"
                  v-model="loginForm.username"
                  placeholder="用户名"
                  @blur="validateField('username')"
                />
              </view>
              <view class="error-message" v-if="errors.username">{{ errors.username }}</view>
            </view>
            
            <view class="form-item PasswordNotNull">
              <view class="PassWord">
                <image src="/static/password.png" class="Logo"></image>
                <input
                  class="Input"
                  type="password"
                  v-model="loginForm.password"
                  placeholder="密码"
                  @blur="validateField('password')"
                  @confirm="handleLogin"
                />
              </view>
              <view class="error-message" v-if="errors.password">{{ errors.password }}</view>
            </view>
            
            <view class="form-item">
              <button class="LoginBtn" form-type="submit">登录</button>
            </view>
          </view>
        </form>
      </view>
    </view>
  </view>
</template>

<script setup>
import { reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { loginRules } from '/utils/validator.js'


// 初始化 store 和 router
const store = useStore()
const router = useRouter()

// 表单数据
const loginForm = reactive({
  username: '',
  password: ''
})

// 错误信息
const errors = reactive({
  username: '',
  password: ''
})

// 验证单个字段
const validateField = (field) => {
  errors[field] = ''
  
  // 使用从 validator 导入的规则进行验证
  if (field === 'username' && !loginForm.username) {
    errors[field] = loginRules.usernameRules[0].message
  }
  
  if (field === 'password' && !loginForm.password) {
    errors[field] = loginRules.passwordRules[0].message
  }
}

// 验证整个表单
const validateForm = () => {
  // 先清空所有错误信息
  Object.keys(errors).forEach(key => {
    errors[key] = ''
  })
  
  let isValid = true
  
  // 验证每个字段
  if (!loginForm.username) {
    errors.username = loginRules.usernameRules[0].message
    isValid = false
  }
  
  if (!loginForm.password) {
    errors.password = loginRules.passwordRules[0].message
    isValid = false
  }
  
  return isValid
}

// 登录事件
const handleLogin = () => {
  // 使用自定义验证
  if (validateForm()) {
    // 表单验证通过，执行登录逻辑
    store
      .dispatch('app/login', loginForm)
      .then(() => {
        store.dispatch('app/setMenuList')
        setTimeout(() => {
          router.replace('/pages/home/home')
        }, 1200)
      })
      .catch(() => {
        uni.showToast({
          title: '账号或密码错误',
          icon: 'none',
          duration: 3000
        })
      })
  } else {
    // 显示错误通知
    uni.showToast({
      title: '请输入正确的用户名密码',
      icon: 'none',
      duration: 3000
    })
    return false
  }
}
</script>

<style>
.LoginBackground {
  display: flex;
  justify-content: center;
  align-items: center;
  background: url("/static/LoginBackground.jpg") no-repeat center center;
  background-size: cover;
  width: 100vw;
  height: 100vh;
}

.LoginForm {
  width: 90%;
  max-width: 800px;
  background: url("/static/Login.png") no-repeat center center;
  background-size: contain;
  aspect-ratio: 800/599;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.2);
  border-radius: 20px;
}

.big-contain {
  width: 100%;
  max-width: 600px;
  padding: 0 30px;
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  box-sizing: border-box;
}

.bform {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.UserName,
.PassWord {
  display: flex;
  align-items: center;
  background-color: rgba(255, 255, 255, 0.8);
  border: 2px solid #2abeb2;
  border-radius: 12px;
  padding: 10px 18px;
  box-sizing: border-box;
  box-shadow: 0 2px 6px rgba(42, 190, 178, 0.2);
  transition: all 0.3s ease;
}

.UserName:focus-within,
.PassWord:focus-within {
  border-color: #1c9c90;
  box-shadow: 0 0 8px rgba(28, 156, 144, 0.3);
}

.Logo {
  width: 28px;
  height: 28px;
  margin-right: 12px;
  opacity: 0.9;
}

.Input {
  flex: 1;
  height: 40px;
  font-size: 17px;
  color: #2abeb2;
  background: transparent;
  border: none;
  outline: none;
}

input::placeholder {
  color: #a0dcd7;
}

.LoginBtn {
  background: url("/static/LoginButton.png") no-repeat center center;
  background-size: contain;
  width: 50%;
  max-width: 220px;
  height: 50px;
  margin: 10px auto;
  display: block;
  border: none;
  outline: none;
  cursor: pointer;
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.LoginBtn:active {
  transform: scale(0.96);
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.15);
}

.error-message {
  color: #ff4d4f;
  font-size: 13px;
  margin-top: 5px;
  padding-left: 10px;
  font-weight: 500;
  transition: opacity 0.3s ease;
}

@media (max-width: 768px) {
  .Input {
    font-size: 15px;
  }

  .LoginBtn {
    height: 45px;
  }

  .Logo {
    width: 24px;
    height: 24px;
  }
}

@media (max-width: 480px) {
  .Input {
    font-size: 14px;
  }

  .LoginBtn {
    width: 60%;
    height: 42px;
  }

  .Logo {
    width: 22px;
    height: 22px;
  }
}

</style>