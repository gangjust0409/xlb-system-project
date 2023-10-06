package org.xlb.study.thead.synchronize;

/**
 * 黑妞党
 */
public class SocietyHeiNiu extends Thread {

    @Override
    public void run() {
        while (true) {
            synchronized (GlobalVariables.lock) {
                if (GlobalVariables.getTicketCountRemaining() <= 0) {
                    break;
                }
                GlobalVariables.subtraction();
                System.out.printf("黑牛党（%s）抢了1票，还剩%s票。\n", Thread.currentThread().getName(), GlobalVariables.getTicketCountRemaining());
            }
        }
    }
}
