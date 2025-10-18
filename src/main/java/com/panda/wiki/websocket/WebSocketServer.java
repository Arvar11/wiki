package com.panda.wiki.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/ws/{token}")
public class WebSocketServer {
    private static final Logger LOG = LoggerFactory.getLogger(WebSocketServer.class);  // 修正 Logger

    /**
     * 每个客户端一个token
     */
    private String token = "";
    
    /**
     * 使用线程安全的 ConcurrentHashMap
     */
    public static ConcurrentHashMap<String, Session> map = new ConcurrentHashMap<>();

    /**
     * 连接成功
     */
    @OnOpen  // 修正注解大小写
    public void onOpen(Session session, @PathParam("token") String token) {  // 修正参数语法
        map.put(token, session);
        this.token = token;
        LOG.info("有新连接：token：{}，session id：{}，当前连接数：{}", token, session.getId(), map.size());  // 修正日志语法
    }

    /**
     * 连接关闭
     */
    @OnClose  // 修正注解大小写
    public void onClose(Session session) {
        map.remove(this.token);
        LOG.info("连接关闭，token：{}，session id：{}，当前连接数：{}", this.token, session.getId(), map.size());  // 修正日志语法
    }

    /**
     * 收到消息
     */
    @OnMessage
    public void onMessage(String message, Session session) { 
        LOG.info("收到消息: {}，内容: {}", token, message);  // 修正日志语法
    }

    /**
     * 连接错误
     */
    @OnError
    public void onError(Session session, Throwable error) { 
        LOG.error("发生错误", error); 
    }

    /**
     * 群发消息
     */
    public static void sendInfo(String message) {  // 改为静态方法
        for (String token : map.keySet()) {
            Session session = map.get(token);
            try {
                if (session.isOpen()) {  // 检查会话是否仍然打开
                    session.getBasicRemote().sendText(message);
                    LOG.info("推送消息: {}, 内容: {}", token, message);  // 修正日志文字
                } else {
                    // 如果会话已关闭，从map中移除
                    map.remove(token);
                }
            } catch (IOException e) {
                LOG.error("推送消息失败: {}, 内容: {}", token, message, e);  // 修正日志文字
                // 发送失败，从map中移除无效连接
                map.remove(token);
            }
        }
    }

    /**
     * 向指定用户发送消息
     */
    public static void sendToUser(String token, String message) {
        Session session = map.get(token);
        if (session != null && session.isOpen()) {
            try {
                session.getBasicRemote().sendText(message);
                LOG.info("向用户 {} 发送消息: {}", token, message);
            } catch (IOException e) {
                LOG.error("向用户 {} 发送消息失败", token, e);
                map.remove(token);  // 移除无效连接
            }
        } else {
            LOG.warn("用户 {} 不在线或连接已关闭", token);
            if (session != null) {
                map.remove(token);  // 清理无效连接
            }
        }
    }

    /**
     * 获取当前在线连接数
     */
    public static int getOnlineCount() {
        return map.size();
    }

    /**
     * 检查用户是否在线
     */
    public static boolean isOnline(String token) {
        Session session = map.get(token);
        return session != null && session.isOpen();
    }
}