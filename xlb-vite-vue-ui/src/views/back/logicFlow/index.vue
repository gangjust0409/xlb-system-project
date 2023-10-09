<template>
    <div class="app-box">
        <div class="container" ref="container"></div>
        <div class="tools top-tools-box">
            <div class="tools-item">
                <el-button type="primary" @click="exportBpmn">导出BPMN</el-button>
            </div>
            <div class="tools-item">
                <el-button @click="exportImage">导出图片</el-button>
            </div>
        </div>
        <div class="tools left-tools-box">
            左侧工具栏
        </div>
        <div class="tools right-tools-box">
            右侧工具栏
        </div>
        <div class="tools bottom-tools-box">
            底部工具栏
        </div>
    </div>
</template>

<script setup lang="ts">
interface LogicFlowType {
    nodes: any[],
    edges?: any[]
}

import { ref, reactive, onMounted } from 'vue'
import { useLogicFlow } from '@/hooks/useLogincflow'
import LogicFlow from "@logicflow/core";

const container = ref();
const lf = ref<LogicFlow>();

//示例
const options = reactive<LogicFlowType>({
    nodes: [
        {
            id: "1",
            type: "rect",
            x: 100,
            y: 100,
            text: "节点1",
        },
        {
            id: "2",
            type: "circle",
            x: 300,
            y: 200,
            text: "节点2",
        },
        {
            id: "3",
            type: "polygon",
            x: 600,
            y: 400,
            text: "节点3",
        },
        {
            id: "4",
            type: "customNode",
            x: 800,
            y: 500,
            text: "自定义节点"
        },
    ],
    edges: [
        {
            sourceNodeId: "1",
            targetNodeId: "2",
            type: "polyline",
            text: "连线",
        },
        {
            sourceNodeId: "2",
            targetNodeId: "3",
            type: "polyline",
            text: "连线",
        },
        {
            sourceNodeId: "3",
            targetNodeId: "4",
            type: "polyline",
            text: "关联",
        },
    ],
})

//bpmn
const dataOptions = reactive<LogicFlowType>({
    nodes: [
        {
            id: 1,
            type: "bpmn:startEvent",
            x: 100,
            y: 100,
            text: "开始",
        },
        {
            id: 10,
            type: "bpmn:endEvent",
            x: 250,
            y: 100,
            text: "结束",
        },
        {
            id: 2,
            type: "bpmn:userTask",
            x: 400,
            y: 100,
            text: "用户任务",
        },
        {
            id: 3,
            type: "bpmn:serviceTask",
            x: 550,
            y: 100,
            text: "系统任务",
        },
        {
            id: 5,
            type: "bpmn:exclusiveGateway",
            x: 660,
            y: 100,
        },
    ],
    edges: [
        // {
        //     sourceNodeId: "1",
        //     targetNodeId: "2",
        //     type: "polyline"
        // },
        // {
        //     sourceNodeId: "2",
        //     targetNodeId: "3",
        //     type: "polyline"
        // },
        // {
        //     sourceNodeId: "3",
        //     targetNodeId: "5",
        //     type: "polyline"
        // },
        // {
        //     sourceNodeId: "5",
        //     targetNodeId: "10",
        //     type: "polyline"
        // },
    ]
});

//导出 bpmn
function exportBpmn() {
    const bpmnData = lf.value!.getGraphData() as string;
    //下载
    download('bpmn.bpmn', bpmnData, 'text:bpmn');
}
//导出图片
function exportImage() {
    lf.value!.getSnapshot('','#fff');
    ElMessage({
        type: 'success',
        message: '导出图片成功！'
    })
}

//下载 导出的时候，提示可以自定义文件名
function download(fileName: string, content: string, type: any) {
    let blob = new Blob([content], { type });
    //根据Blob生成下载路径
    let bpnmUrl = URL.createObjectURL(blob);
    //创建一个a标签，用于下载
    const aTag = document.createElement('a');
    //下载地址
    aTag.href = bpnmUrl;
    //TODO保存后的文件，文件已经下载成功了
    aTag.download = fileName;
    aTag.click();
    //移除
    URL.revokeObjectURL(bpnmUrl);
    ElMessage({
        type: 'success',
        message: '导出成功！'
    })
}

onMounted(() => {
    //lf实例
    lf.value = useLogicFlow(container.value, dataOptions);
});


</script>

<style scoped lang="scss">
.container {
    width: 100%;
    height: 100%;
}

.app-box {
    position: relative;
    height: 80vh;

    .tools {
        position: absolute;
        background-color: #fff;
        padding: 10px;
        display: flex;

        &.top-tools-box {
            width: 100%;
            top: 5px;
            left: 50%;
            transform: translateX(-50%);
        }

        &.bottom-tools-box {
            width: 100%;
            bottom: 5px;
            left: 50%;
            transform: translateX(-50%);
        }

        &.left-tools-box {
            top: 50%;
            left: 10px;
            transform: translateY(-50%);
        }

        &.right-tools-box {
            top: 50%;
            right: 10px;
            transform: translateY(-50%);
        }
    }
}
</style>