import { ECOption } from '@/utils/echarts'
import * as echarts from 'echarts'


export function render(dom: HTMLElement, options: ECOption) {
    if (!dom) {
        Promise.reject("dom 元素不能为空")
        return;
    }
    if (!options || (options && options.length <= 0)) {
        Promise.reject("options 错误！")
        return;
    }
    const myChart =  echarts.init(dom);
    myChart.setOption(options);
    return myChart;
}

