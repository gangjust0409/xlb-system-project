import LogicFlow from "@logicflow/core";
//logicflow 插件 BPMN
import { BpmnElement, BPMNAdapter, Snapshot } from "@logicflow/extension";
//自定义节点
import customNode from '@/hooks/customNode'

interface LogicFlowType {
    nodes: any[],
    edges?: any[]
}

//logicFlow 实例
export function useLogicFlow(dom: HTMLElement, options: LogicFlowType): LogicFlow {
    //全局引入
    LogicFlow.use(BpmnElement);
    LogicFlow.use(BPMNAdapter);
    LogicFlow.use(Snapshot);
    const lf = new LogicFlow({
        container: dom,
        grid: true,
    });
    console.log(options);

    //注册自定义节点
    lf.register(customNode);
    //渲染图原始数据，和render的区别是在使用adapter后，如何还想渲染 logicflow 格式的数据，可以用此方法。
    // lf.render(options);
    lf.renderRawData(options);
    return lf;
}

