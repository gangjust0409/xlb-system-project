//自定义node节点
// 菱形
import { RectNode, RectNodeModel,h } from "@logicflow/core";
//对节点缩放的逻辑
import { RectResize } from "@logicflow/extension";

//创建视图、模型
class customNodeView extends RectNode {
    //label 图标
    private getLabelShape() {
        const { model } = this.props;
        const { x, y, width, height } = model;
        const style = model.getNodeStyle();
        return h(
            "svg",
            {
                x: x - width / 2 + 5,
                y: y - height / 2 + 5,
                width: 25,
                height: 25,
                viewBox: "0 0 1274 1024"
            },
            h("path", {
                fill: style.stroke,
                d:
                    "M655.807326 287.35973m-223.989415 0a218.879 218.879 0 1 0 447.978829 0 218.879 218.879 0 1 0-447.978829 0ZM1039.955839 895.482975c-0.490184-212.177424-172.287821-384.030443-384.148513-384.030443-211.862739 0-383.660376 171.85302-384.15056 384.030443L1039.955839 895.482975z"
            })
        );
    }
    //自定义样式
    getShape() {
        const { model, graphModel } = this.props;
        const { x, y, width, height, radius } = model;
        const style = model.getNodeStyle();
        return h("g", {}, [
            h("rect", {
                ...style,
                x: x - width / 2,
                y: y - height / 2,
                rx: radius,
                ry: radius,
                width,
                height
            }),
            this.getLabelShape()
        ]);
    }
}

class customNodeModel extends RectNodeModel {
    //自定义形状样式
    getNodeStyle() {
        const style = super.getNodeStyle();
        style.stroke = "green";
        style.strokeDasharray = "3 3";
        style.fill = "green";
        const properties = this.properties;
        if (properties.statu === "pass") {
            style.stroke = "green";
        } else if (properties.statu === "reject") {
            style.stroke = "red";
        } else {
            style.stroke = "rgb(24, 125, 255)";
        }
        return style;
    }
    //文本样式
    getTextStyle() {
        const style = super.getTextStyle();
        style.fontSize = 16;
        return style;
    }
    //自定义形状
    initNodeData(data: any) {
        super.initNodeData(data);
        console.log(data);
        this.width = 100;
        this.height = 80;
        this.radius = 5;
    }
}

export default {
    type: "customNode",
    view: customNodeView,
    model: customNodeModel
}