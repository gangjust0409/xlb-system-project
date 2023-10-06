import { createRouter, createMemoryHistory, createWebHistory } from 'vue-router'

const pages = import.meta.glob("@/views/**/*.vue");

const rs = Object.keys(pages).map(page => {
    // /src/views/**/*.vue
    let name:string = String(page.match(/\/views(.*)\.vue$/)![1]).toLowerCase()
    return {
        path: name == "/back/_layout" ? '/' : name,
        component: pages[page]
    }
    
})
// console.log(rs);


const routes:any[] = [
    {
        path: '',
        redirect: '/logicFlow'
    },
    {
        path: '/',
        component: () => import('@/views/back/_layout.vue'),
        children: [
            {
                path: 'index',
                component: () => import('@/views/back/weclome/index.vue')
            },
            {
                path: 'logicFlow',
                component: () => import('@/views/back/logicFlow/index.vue')
            },
        ]
    }
];


const router = createRouter({
    history: import.meta.env.SSR ? createMemoryHistory() : createWebHistory(),
    routes,
})
export default router;