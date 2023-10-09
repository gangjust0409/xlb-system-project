<template>
    <div class="app-layout">
        <el-container>
            <el-aside class="aside-menu">
                <h2>
                    <img v-show="isLogo" src="@/assets/vue.svg" alt="logo" class="logo">
                    <span :class="{ 'fade-system-title': true, 'show': isTitleFlag == 1 }">后台管理系统</span>
                </h2>
                <el-menu default-active="2" class="el-menu-vertical-demo" :collapse="isCollapse" @open="handleOpen"
                    @close="handleClose">
                    <sub-menu :list="menuStore.menus"></sub-menu>
                </el-menu>
            </el-aside>
            <el-container>
                <el-header>
                    <el-row class="xlb-header" align="middle">
                        <el-col :span="4">
                            <el-icon :size="20" class="icon" v-show="!isCollapse" @click="changeCollapse(true)">
                                <i-ep-fold />
                            </el-icon>
                            <el-icon :size="20" class="icon" v-show="isCollapse" @click="changeCollapse(false)">
                                <i-ep-Expand />
                            </el-icon>
                        </el-col>
                        <el-col :span="8" :offset="12" class="other-info">
                            <div class="other-item">
                                <el-popover placement="bottom" :width="350" trigger="hover">
                                    <template #reference>
                                        <el-badge is-dot class="item">
                                            <el-icon :size="20" class="icon"><i-ep-bell /></el-icon>
                                        </el-badge>
                                    </template>
                                    <!-- 内容 -->
                                    <template #default>
                                        <section class="notify-box">
                                            <div class="setting-box">
                                                <el-badge :value="12" class="item">
                                                    <h4>消息通知</h4>
                                                </el-badge>
                                                <el-link type="info">一键已读</el-link>
                                            </div>
                                            <div class="notify-content">
                                                <div class="notiry-item readed">
                                                    <div class="notiry-title">中秋月饼大赠送！随便吃，哈哈秋月饼大赠送！随便吃，哈哈</div>
                                                    <el-tag type="warning" class="notiry-createdTime">2023-05-23</el-tag>
                                                    <span class="readed-text">已读</span>
                                                </div>
                                                <div class="notiry-item">
                                                    <div class="notiry-title">中秋月饼大赠送！随便吃，哈哈秋月饼大赠送！随便吃，哈哈</div>
                                                    <el-tag type="warning" class="notiry-createdTime">2023-05-23</el-tag>
                                                </div>
                                            </div>
                                        </section>
                                    </template>
                                </el-popover>
                            </div>
                            <div class="other-item">
                                <el-dropdown>
                                    <template #default>
                                        <img src="@/assets/images/user01.jpg" alt="" class="pic">
                                    </template>
                                    <template #dropdown>
                                        <el-dropdown-menu>
                                            <el-dropdown-item>用户中心</el-dropdown-item>
                                            <el-dropdown-item>修改密码</el-dropdown-item>
                                            <el-dropdown-item>退出登录</el-dropdown-item>
                                        </el-dropdown-menu>
                                    </template>
                                </el-dropdown>
                            </div>
                            <div class="other-item">
                                <el-icon :size="20" class="icon"><i-ep-setting /></el-icon>
                            </div>

                        </el-col>
                    </el-row>
                </el-header>
                <el-main><router-view /></el-main>
                <el-footer>
                    <el-row :span="24">
                        <el-col style="text-align:center;"> &copy; lqg后台管理系统</el-col>
                    </el-row>
                </el-footer>
            </el-container>
        </el-container>
    </div>
</template>

<script setup lang="ts">
import SubMenu from '@/components/SubMenu.vue'
import {userMenuStore} from '@/pinia/menu'

const menuStore = userMenuStore();
//是否显示logo或文字
const isLogo = ref<Boolean>(false);
const isTitleFlag = ref<Number>(1);
const isCollapse = ref<Boolean>(false)
//菜单宽度
const menuWidth = ref<string>('200px');
console.log(menuStore);




//显示隐藏 ，根据是否设置logo
function changeCollapse(flag: boolean) {
    isCollapse.value = flag;
    if (isCollapse.value) {
        isLogo.value = true;
        isTitleFlag.value = '0';
        menuWidth.value = '64px';
    } else {
        isLogo.value = false;
        menuWidth.value = '200px';
        // setTimeout(() => {
        isTitleFlag.value = '1';
        // }, 300)
    }
}

//
const handleOpen = (key: string, keyPath: string[]) => {
    console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
    console.log(key, keyPath)
}
</script>

<style lang="scss">
.app-layout {
    .fade-system-title {
        opacity: 0;
        transition: opacity .7s ease;
        display: none;

        &.show {
            opacity: v-bind(isTitleFlag);
            display: block;
        }
    }

    .aside-menu {
        width: v-bind(menuWidth);
        transition: all 0.3s;
    }

    h2 {
        width: v-bind(menuWidth);
        display: flex;
        align-items: center;
        justify-content: center;
        transition: all 0.3s;
        margin: .9688rem auto;

        &>.logo {
            width: 2.1875rem;
            height: 1.875rem;
            margin-right: .3125rem;
        }

        span {
            font-weight: bolder;
            color: var(--el-color-primary);
        }
    }

    .other-info {
        display: flex;
        align-items: center;
        justify-content: flex-end;

        .other-item {
            margin-right: .9375rem;
            cursor: pointer;


            .pic {
                width: 2.1875rem;
                height: 2.1875rem;
                border-radius: 5px;
            }
        }

    }
}

.notify-box {

    .el-badge__content.is-fixed {
        top: 4px !important;
    }

    .setting-box {
        display: flex;
        justify-content: space-between;
        align-items: center;

        h4 {
            margin-top: 0.625rem;
            margin-bottom: .625rem;
        }

    }

    .notify-content {
        padding-top: .625rem;
        border-top: 1px solid var(--info-text-color);

        .notiry-item {
            display: flex;
            align-items: center;
            font-size: 14px;
            color: var(--info-text-color);
            margin-bottom: .625rem;
            cursor: pointer;

            .notiry-title {
                width: 12.5rem;
                white-space: nowrap;
                text-overflow: ellipsis;
                overflow: hidden;
                margin-right: .3125rem;
            }

            &.readed {
                .notiry-title {
                    color: #909399;
                }

                span.readed-text {
                    color: #909399;
                    font-size: 0.75rem;
                    margin-left: .3125rem;
                }
            }
        }
    }
}
</style>