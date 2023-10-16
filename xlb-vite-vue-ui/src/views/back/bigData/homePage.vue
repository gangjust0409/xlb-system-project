<template>
    <div class="bg-data">
        <header class="bg-data-header">
            <h1>后台管理系统————大数据可视化界面</h1>
        </header>
        <!-- 主体 -->
        <sectioin class="container">
            <!-- 左 -->
            <section class="item-left">
                <common-chat-position>
                    <common-chat title="2023年10月各种品牌销售详细信息" :option="data" type="bar"/>
                </common-chat-position>
                <common-chat-position>
                    <common-chat title="图标2" />
                </common-chat-position>
            </section>
            <!-- 中 -->
            <section class="item-center">
                <div class="china-map"></div>
            </section>
            <!-- 由 -->
            <section class="item-right">
                <common-chat-position>
                    <common-chat title="图标3" />
                </common-chat-position>
                <common-chat-position>
                    <common-chat title="图标4" />
                </common-chat-position>
            </section>
        </sectioin>
    </div>
</template>
<script lang="ts" setup>
type BarType = {
    xData?: any[],
    yData?: any[],
    type?: string
}

import {reactive,ref,onMounted} from 'vue'
import CommonChatPosition from "@/components/CommonChatPosition.vue";
import CommonChat from '@/components/CommonChat.vue'



//销售
const bar = ref<BarType>({});
const data = [
    {
        title: '电视机',
        num: 167
    },
    {
        title: '电话',
        num: 456
    },
    {
        title: '手机',
        num: 500
    },
    {
        title: '烘烤箱',
        num: 240
    },
    {
        title: '手表',
        num: 156
    },
    {
        title: '洗衣机',
        num: 560
    }
]

function handleEchartsOptions(){
    bar.value.xData = data.map(da => da.title);
    bar.value.yData = data.map(da => da.num);
    bar.value.type = 'bar';
}
onMounted(() => {
    handleEchartsOptions();
})


</script>
<style scoped lang="scss">
.bg-data {
    height: 100vh;
    background: url('@/assets/images/bg.jpg') top center no-repeat;
    box-sizing: border-box;

    .bg-data-header {
        width: 100%;
        height: 1rem;
        line-height: 1rem;
        background-color: rgba(0, 0, 255, .2);

        h1 {
            margin: 0;
            text-align: center;
            font-size: .375rem;
            line-height: 1rem;
            color: #fff;
        }

    }

}

// 主体
.container {
    min-width: 1080px;
    max-width: 1920px;
    display: flex;
    padding: .375rem .375rem 0;
    margin: 0 auto;

    .item-left,
    .item-right {
        flex: 5;
    }

    .item-center {
        flex: 9;
        margin: 0 .25rem;
        border: 1px solid blue;
    }

}
</style>