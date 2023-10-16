import { createSSRApp } from 'vue'
import App from '@/App.vue'
import "@logicflow/core/dist/style/index.css";
import '@logicflow/extension/lib/style/index.css';
import { createPinia } from 'pinia'
//flexible
import 'lib-flexible/flexible.js'


import router from './router'

export function createApp() {

    const app = createSSRApp(App);
    const pinia = createPinia();

    app.use(router);
    app.use(pinia);

    return {
        app, router, pinia
    }
}

