/**
 * Created by Congwz on 2019/4/2.
 */

class MyRunnable implements Runnable {

    /**
     * 共享同一资源
     */
    private int ticketCount = 5; //一共有5张火车票

    @Override
    public void run() {  //线程进入运行状态
        while(ticketCount > 0) {
            ticketCount--; //如果还有票，就卖掉一张
            System.out.println(Thread.currentThread().getName() + "卖掉1张票，剩余票数为：" + ticketCount);
        }
    }
}

/*
线程生命周期：
创建状态（new） -> 就绪状态（start） -> 运行状态（run,获取cpu分片） -> 终止状态

其中：
遇到阻塞：运行状态 -> 阻塞状态
阻塞解除：阻塞状态 -> 就绪状态
* */


public class TicketRunnableDemo {

    public static void main(String[] args) {
        MyRunnable mr = new MyRunnable();

        /*这样ticketCount就不会是共享资源了*/
        MyRunnable mr2 = new MyRunnable();
        MyRunnable mr3 = new MyRunnable();

        //创建三个线程，模拟三个售票窗口
        Thread mt1 = new Thread(mr,"窗口1");
        /*Thread mt2 = new Thread(mr,"窗口2");
        Thread mt3 = new Thread(mr,"窗口3");*/
        Thread mt2 = new Thread(mr2,"窗口2");
        Thread mt3 = new Thread(mr3,"窗口3");


        //启动这3个线程，即三个窗口开始卖票
        mt1.start();  //线程变成就绪状态，进入线程队列中
        mt2.start();
        mt3.start();
    }


}
