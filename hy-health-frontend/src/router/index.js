import {createRouter, createWebHistory} from 'vue-router'
import Login from '../views/Login.vue'
import Layout from "../layout/index.vue";
import HomeIndex from '../views/Home/index.vue'

export const constantRoutes = [
    {
        path: '/user/login',
        name: 'login',
        component: Login,
        meta: {
            title: '登录',
            keepAlive: false
        }
    },
    {
        path: '/',
        name: 'layout',
        component: Layout,
        children: [
            {
                path: '/',
                name: 'home',
                component: HomeIndex,
                meta: {title: '首页'}
            }
        ]
    }
]

// 创建路由实例
const router = createRouter({
    history: createWebHistory(
        process.env.NODE_ENV === 'production' ? '/base/' : '/'
    ),
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
