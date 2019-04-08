/**
 * Created by Congwz on 2019/4/2.
 */

/**
 * 线程类
 */
class MyThread extends Thread {

    /**
     * 独立的资源，不共享
     */
    private int ticketCount = 5; //一共有5张火车票

    private String name;  //窗口即线程的名字

    public MyThread(String name) {
        this.name = name;
    }

    @Override
    public void run () {
        while(ticketCount > 0) {
            ticketCount--; //如果还有票，就卖掉一张
            System.out.println(name + "卖掉1张票，剩余票数为：" + ticketCount);
        }
    }

}

public class TicketThreadDemo {

    public static void main(String[] args) {
        //创建三个线程，模拟三个窗口卖票
        MyThread mt1 = new MyThread("窗口1");
        MyThread mt2 = new MyThread("窗口2");
        MyThread mt3 = new MyThread("窗口3");

        //启动三个线程，即窗口开始卖票
        mt1.start();
        mt2.start();
        mt3.start();

    }
}
