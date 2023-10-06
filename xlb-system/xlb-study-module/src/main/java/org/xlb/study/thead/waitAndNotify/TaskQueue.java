package org.xlb.study.thead.waitAndNotify;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 任务
 */
public class TaskQueue {

    private Queue<String> tasks = new LinkedList<>();

    //添加任务 synchronized 没有节点多线程协调问题
    public synchronized void addTask(String task) {
        this.tasks.add(task);
    }

    //获取第一个任务
    public String getTask() throws InterruptedException {
        //为空继续等待
        if (tasks.isEmpty()) {
            this.wait();
        }
        return tasks.remove();
    }

}
