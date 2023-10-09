import { createApp } from 'vue'
import './style.css'
import 'amfe-flexible'
import App from './App.vue'
import router from './router'

const app = createApp(App)
app.use(router);

app.mount('#app')
