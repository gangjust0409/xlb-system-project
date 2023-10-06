package org.xlb.study.thead.synchronize;

public class GlobalVariables {

    public static Object lock = new Object();

    /**
     * 票数
     */
    private static Long ticketCount = 100L;

    /**
     * 剩余票数
     */
    public static Long getTicketCountRemaining(){
        return ticketCount;
    }
    /**
     * 票数减减
     */
    public static void subtraction(){
        if (ticketCount > 0){
            ticketCount--;
        }
    }


}
