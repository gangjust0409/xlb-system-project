<template>
    <div class="container" ref="container"></div>
</template>

<script setup lang="ts">
interface LogicFlowType{
    nodes: any[],
    edges?: any[]
}

import { ref, reactive, onMounted } from 'vue'
import { useLogicFlow } from '@/hooks/useLogincflow'
const container = ref();

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

onMounted(() => {
    const logicFlowContainer = useLogicFlow(container.value,options);
    // console.log(logicFlowContainer);

});


</script>

<style scoped>
.container{
    width: 100%;
    height: 100%;
}
</style>