package org.xlb.study.thead.synchronize;

/**
 * 黄牛党
 */
public class SocietyHuangNiu extends Thread {

    @Override
    public void run() {
        while (true) {
            //加锁：不同的业务逻辑加不同的锁，有利于提高运行效率
            synchronized (GlobalVariables.lock) { // synchronized 保证在任意时刻只有一个线程在执行，因为是多个线程，参数里就在全局类中定义一个对象作为锁
                if (GlobalVariables.getTicketCountRemaining() <= 0) {
                    break;
                }
                GlobalVariables.subtraction();
                System.out.printf("黄牛党（%s）抢了1票，还剩%s票。\n", Thread.currentThread().getName(), GlobalVariables.getTicketCountRemaining());
            }
        } //执行到这里，不管是否有异常都会释放锁
    }
}
