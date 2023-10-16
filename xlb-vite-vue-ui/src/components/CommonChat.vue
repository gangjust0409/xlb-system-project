<template>
    <div class="chat-box">
        <!-- 标题 -->
        <h3>{{ title }}</h3>
        <!-- 图标 DOM -->
        <div class="chat-instant" ref="chat" id="chat"></div>
    </div>
</template>

<script setup lang="ts">
import { ref, reactive, defineProps, onMounted } from 'vue'
import { render } from '@/hooks/useEcharts'
import * as echarts from 'echarts'
import { ECOption } from '@/utils/echarts'

const chat = ref();


const { title, option, type } = defineProps<{
    title: string,
    type: string,
    option: any
}>();
let xData = ref({});
let yData = ref({});



const options = reactive<ECOption>({
    xAxis: {
        type: 'value'
    },
    yAxis: {
        type: 'category',
        data: xData.value
    },
    series: [
        {
            type: 'bar',
            data: yData.value
        }
    ]
});

//加载dom后
onMounted(() => {

    if (type == 'bar') {
        xData.value = option.map((da: any) => da.title);
        yData.value = option.map((da: any) => da.num);
    }
    console.log(xData.value, yData.value);
    echarts.init(chat.value).setOption({
        xAxis: {
            type: 'value'
        },
        yAxis: {
            type: 'category',
            data: xData.value
        },
        series: [
            {
                type: 'bar',
                data: yData.value
            }
        ]
    });


    // const myChart = render(chat.value, options);
})



</script>

<style lang="scss" scoped>
.chat-box {

    h3 {
        margin: 0;
        text-align: center;
        height: .25rem;
        line-height: .25rem;
        color: #fff;
        font-family: "微软雅黑";
        letter-spacing: 1px;
    }

    .chat-instant {
        height: 4.5rem;

    }
}
</style>