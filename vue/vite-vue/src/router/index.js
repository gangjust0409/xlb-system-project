import { createWebHashHistory, createRouter } from 'vue-router'

const routes = [
    {
        papth:'/',
        redirect: "/layout/index"
    },
    {
        path: "/layout",
        component: ()=> import('@/pages/layout.vue'),
        children: [
            {
                path: "index",
                component: () => import('@/pages/view/index.vue')

            }
        ]
    }
];


const router = createRouter({
    history: createWebHashHistory(),
    routes
});


export default router;