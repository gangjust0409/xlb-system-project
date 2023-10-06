import { defineConfig, loadEnv } from 'vite'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'
import ElementPlus from 'unplugin-element-plus/vite'
import Icons from 'unplugin-icons/vite'
import IconsResolver from 'unplugin-icons/resolver'
import vue from '@vitejs/plugin-vue'
import path from 'path'

// https://vitejs.dev/config/
export default defineConfig((({ command, mode }) => {
  return {
    base: './',
    resolve: {
      alias: {
        //主目录
        '@': path.resolve(__dirname, './src'),
        '~/': `${path.resolve(__dirname, 'src')}/`,
      }
    },
    //配置样式
    css: {
      preprocessorOptions: {
        scss: {
          //样式主题按需加载
          additionalData: `@use "~/styles/element/index.scss" as *;`,
        }
      }
    },
    //vite 插件
    plugins: [
      //vue
      vue(),
      //自动导入
      AutoImport({
        //自动导入ref、reactive
        imports: ['vue'],
        resolvers: [
          // 自动导入 Element Plus 相关函数，如：ElMessage, ElMessageBox... (带样式)
          ElementPlusResolver(),
          //自动引入图标
          IconsResolver({
            prefix: 'Icon'
          })
        ]
      }),
      //组件
      Components({
        resolvers: [
          // 自动注册图标组件
          IconsResolver({
            enabledCollections: ['ep'],
          }),
          // 自动导入 Element Plus 组件
          ElementPlusResolver(),
        ],
      }),
      Icons({
        autoInstall: true,
      }),
      //主题
      ElementPlus({
        // options
        useSource: true,
      }),
    ],
    //vite配置
    server: {
      port: 80,
      host: '127.0.0.1'
    },
  }
}))
