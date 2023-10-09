<template>
    <!-- 单个 -->
    <el-menu-item index="2">
        <el-icon><i-ep-document /></el-icon>
        <template #title>主页</template>
    </el-menu-item>
    <!-- 多个 -->
    <el-sub-menu index="1">
        <template #title>
            <el-icon>
                <i-ep-setting />
            </el-icon>
            <span>系统管理</span>
        </template>
        <el-menu-item index="1-1">item one</el-menu-item>
        <el-menu-item index="1-2">item two</el-menu-item>
        <el-menu-item index="1-3">item three</el-menu-item>
        <el-menu-item index="1-4-1">item one</el-menu-item>
        <el-menu-item index="1-4-2">item one</el-menu-item>
    </el-sub-menu>
</template>
<script setup lang="ts">
import { defineProps } from 'vue'

const {list} = defineProps(['list'])
console.log(list);


</script>
<style lang="scss"></style>