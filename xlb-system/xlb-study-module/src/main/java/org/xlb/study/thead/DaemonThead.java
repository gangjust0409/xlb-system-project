package org.xlb.study.thead;

public class DaemonThead extends Thread {

    @Override
    public void run() {
        //谁负责结束循环的线程？守护线程
        while (true) {
            System.out.printf("线程：%s，正在执行！", Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println("线程执行异常");
                break;
            }
        }
    }
}
