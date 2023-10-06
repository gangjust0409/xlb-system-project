package org.xlb.study.thead;

/**
 * 简单线程
 */
public class SimpleThead extends Thread{

    @Override
    public void run() {
        System.out.println("线程：" + Thread.currentThread().getName() + "正在执行！");
    }
}
