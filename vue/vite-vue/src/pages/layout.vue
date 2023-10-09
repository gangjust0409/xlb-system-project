<script setup>
import {ref} from 'vue'
//当前选择的
const active = ref(0);

</script>

<template>
    <div class="app">
        <div class="container">
            <router-view></router-view>
        </div>
        <van-tabbar v-model="active">
            <van-tabbar-item icon="home-o">标签</van-tabbar-item>
            <van-tabbar-item icon="search">标签</van-tabbar-item>
            <van-tabbar-item icon="friends-o">标签</van-tabbar-item>
            <van-tabbar-item icon="setting-o">标签</van-tabbar-item>
        </van-tabbar>
    </div>
</template>

<style scoped></style>