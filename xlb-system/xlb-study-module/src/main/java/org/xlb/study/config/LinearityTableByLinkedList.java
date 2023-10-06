package org.xlb.study.config;

/**
 * 使用单向链表实现线性表
 */
public class LinearityTableByLinkedList {

    //头结点
    private Node head;
    private Integer size; //线性表中的个数

    public LinearityTableByLinkedList() {
        this.head = null;
        this.size = 0;
    }

    public Integer size(){ return size; }

    //添加
    public void add(Object data){
        //创建一个节点
        Node node = new Node(data);
        //判断当前节点是否存在根节点
        if (head == null) {
            head = node;
        } else {
            //当前节点
            Node current = head;
            //保存存到该节点后面
            while (current.next != null) {
                current = current.next;
            }
            current.next = node;

        }
        size++;
    }

    public <T> T get(Integer index){
        Node current = head;
        for (Integer i = 0; i < index; i++) {
            current = current.next;
        }
        return (T)current.data;
    }

    private class Node {
        private Object data;
        private Node next;

        public Node(Object data) {
            this.data = data;
            this.next = null;
        }
    }




}
