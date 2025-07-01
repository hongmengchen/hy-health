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

export default router