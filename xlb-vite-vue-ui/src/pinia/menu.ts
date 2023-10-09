import { defineStore } from 'pinia'

interface MenuType {
    path: string
    component?:any
    redirect?: string
    meta: any
    children: MenuType[]
}


export const  userMenuStore = defineStore("menuStore", {
    state: () => ({
        menus: [
            {
                path: '',
                redirect: '/',
                meta: {
                    hidden: true
                }
            },
            {
                path: '/',
                component: () => import('@/views/back/_layout.vue'),
                meta: {
                    hidden: true
                },
                children: [
                    {
                        path: 'index',
                        component: () => import('@/views/back/weclome/index.vue'),
                        meta: {
                            hidden: false,
                            text: '主页', 
                            order: 1, //排序
                            icon: 'i-ep-document'
                        },
                    },
                    {
                        path: 'logicFlow',
                        component: () => import('@/views/back/logicFlow/index.vue'),
                        meta: {
                            hidden: false,
                            text: '学习管理',
                            order: 3,
                            icon: 'i-ep-documentChecked'
                        },
                    },
                    {
                        path: 'system',
                        component: () => import('@/views/back/system/index.vue'),
                        meta: {
                            hidden: false,
                            text: '系统管理',
                            order: 2,
                            icon: 'i-ep-setting'
                        },
                    },
                ]
            }
        ]<MenuType>,
    }),
    actions: {

    },
    getters: {
        //TOdo
        menusList: (state: any)  => state.menus.filter((item: MenuType) => item.meta.hidden!=true)
    }
});
