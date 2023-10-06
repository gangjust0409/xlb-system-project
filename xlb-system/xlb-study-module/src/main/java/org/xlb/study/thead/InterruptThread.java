package org.xlb.study.thead;

/**
 * runnable 接口实现线程
 */
public class InterruptThread extends Thread {

    @Override
    public void run() {
        int n = 0;
        while (! isInterrupted()) {
            n++;
            System.out.println(n + "次，InterruptThread执行了，线程：" + Thread.currentThread().getName());
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("InterruptThread 线程已停止！");
                break;
            }
        }
    }
}
