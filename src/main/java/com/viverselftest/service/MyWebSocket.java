package com.viverselftest.service;

import org.springframework.stereotype.Component;

import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/webSocket")
@Component
public class MyWebSocket {

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;


    /**
     * 连接建立成功(调用的方法)/(回调)
     * @param session
     */
    public void onOpen(Session session){
        this.session = session;
        //加入set中
        webSocketSet.add(this);
        //在线人数加1
        addOnlineCount();
        System.out.println("有新连接加入，当前在线人数为：" + getOnlineCount());
        try {
            //连接上给客户端发送一个消息
            sendMessage("连接服务器成功啦~");
        } catch (IOException e) {
            System.out.println("IO异常");
            e.printStackTrace();
        }

    }

    /**
     * 连接关闭调用的方法
     */
    public void onClose(){
        //从set中删除
        webSocketSet.remove(this);
        //在线人数减1
        subOnlineCount();
        System.out.println("有一连接关闭，当前在线人数为：" + getOnlineCount());

    }

    /**
     * 接收到客户端消息后调用的方法
     * @param message
     * @param session
     */
    public void onMessage(String message, Session session){
        System.out.println("收到来自客户端的消息：" + message);
        //群发消息
        for (MyWebSocket item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 发生错误时调用的方法
     * @param session
     * @param throwable
     */
    public void onError(Session session, Throwable throwable){
        System.out.println("发生异常！");
        throwable.printStackTrace();
    }


    public static synchronized int getOnlineCount() {
        return onlineCount;
    }
    public static synchronized void addOnlineCount() {
        MyWebSocket.onlineCount++;
    }
    public static synchronized void subOnlineCount() {
        MyWebSocket.onlineCount--;
    }

    /**
     * 统一的发送消息方法
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

}
