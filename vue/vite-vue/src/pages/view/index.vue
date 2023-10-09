<script setup>
import { ref } from 'vue'

const message = ref('');
const messageList = ref([]);


</script>

<template>
    <div class="message-setting">
        <div class="message-box">
            <div class="mesage-list">
                <div class="meessage-item" v-for="(msg, index) in messageList" :key="index">发射点犯得上</div>
            </div>
            <div class="setting-box">
                <van-field v-model="message" placeholder="发送消息" />
                <van-button type="primary">发送</van-button>
            </div>
        </div>
    </div>
</template>
<style scoped>
.message-setting {
    position: relative;
    height: 90vh;
}

.message-setting .message-box {
    position: absolute;
    bottom: 0;
}

.setting-box {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 10px;
}

.mesage-list {
    padding: 0 10px;
}</style>