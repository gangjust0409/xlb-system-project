import LogicFlow from "@logicflow/core";
//自定义节点
import customNode from '@/hooks/customNode'

interface LogicFlowType {
    nodes: any[],
    edges?: any[]
}

//logicFlow 实例
export function useLogicFlow(dom: HTMLElement, options: LogicFlowType) {
    const lf = new LogicFlow({
        container: dom,
        grid: true,
    });
    //注册自定义节点
    lf.register(customNode);
    lf.render(options);
    return lf;
}

