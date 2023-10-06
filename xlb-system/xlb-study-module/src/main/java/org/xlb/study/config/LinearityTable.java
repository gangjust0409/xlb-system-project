package org.xlb.study.config;

import java.util.ArrayList;

/**
 * 使用数组实现线性存储结构（线性表）
 * @author lqg
 */
public class LinearityTable {

    private Object[] array; //数组，存储数据
    private Integer size;  //线性表元素个数

    public LinearityTable(Integer capacity) {
        array = new Object[capacity];
        size = 0;
    }

    //向线性表末尾添加元素
    public void add(Object element) {
        //数组扩容 2 倍
        if (size == array.length) {
            Object[] newArray = new Object[array.length * 2];
            //数组拷贝
            System.arraycopy(newArray, 0, array, 0, size);
            array = newArray;
        }
        array[size] = element;
        size++;
    }

    public <T> T get(Integer index) {
        if (index <= 0 && index >= array.length) {
            throw new IndexOutOfBoundsException("线性表索引越界！");
        }
        return (T)array[index];
    }

    public void remove(Integer index) {
        if (index <= 0 && index >= array.length) {
            throw new IndexOutOfBoundsException("线性表索引越界！");
        }
        //将元素往后移动，
        for(int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        //将最后一个元素移除
        size--;
    }

    public Integer size() {
        return size;
    }
}
