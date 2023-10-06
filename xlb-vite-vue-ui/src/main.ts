import { createSSRApp } from 'vue'
import App from '@/App.vue'
import "@logicflow/core/dist/style/index.css";


import router from './router'

export function createApp() {

    const app = createSSRApp(App);
    app.use(router);
    return {
        app, router
    }
}

