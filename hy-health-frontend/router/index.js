import { createRouter, createWebHashHistory } from 'vue-router'

import login from '/pages/login/login.vue'
import home from '/pages/home/home.vue'

const routes = [
  {
      path: '/pages/login/login',
      name: 'login',
      component: login
    },
	{
	    path: '/',
	    name: 'home',
	    component: () => import('/pages/home/home.vue')
	}
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

// 全局前置守卫：检查登录状态
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token');
  // 未登录且目标不是登录页时跳转登录
  if (!token && to.path !== '/pages/login/login') {
    next('/pages/login/login');
  } else {
    next();
  }
})

export default router