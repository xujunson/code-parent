package com.atu.realtimelog.component;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.atu.realtimelog.config.MyAppender;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Random;

@ServerEndpoint("/webSocket")
@Component
public class WebSocketServer {

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private Integer sessionId;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        this.sessionId = (new Random()).nextInt(100000);
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        // 第二步：获取日志对象 （日志是有继承关系的，关闭上层，下层如果没有特殊说明也会关闭）
        Logger rootLogger = lc.getLogger("root");
        MyAppender myAppender = new MyAppender(this);
        myAppender.setContext(lc);
        myAppender.setName("myAppender" + sessionId);
        myAppender.start();
        rootLogger.addAppender(myAppender);
        System.out.println("注入成功");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = lc.getLogger("root");
        rootLogger.detachAppender("myAppender" + sessionId);
        System.out.println("--------移除成功--------");
    }

    /**
     * 服务器主动发送消息
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }
}
