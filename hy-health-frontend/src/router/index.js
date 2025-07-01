// src/router/index.js
import {createRouter, createWebHistory} from 'vue-router'
import Login from '../views/login.vue'
import HomeIndex from '../views/home/index.vue'

export const constantRoutes = [
    {
        path: '/user/login',
        name: 'login',
        component: Login,
        meta: {
            title: '登录'
            , keepAlive: false
        }
    },
    {
        path: '/',
        name: 'home',
        component: HomeIndex,
        meta: {title: '首页'}
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes: constantRoutes
})

// 判断登录状态
router.beforeEach((to, from, next) => {
    document.title = to.meta.title
    const token = localStorage.getItem('token')
    if (to.path === '/user/login' && token) {
        next('/')
    } else if (to.path !== '/user/login' && !token) {
        next('/user/login')
    } else {
        next()
    }
})

export default router
