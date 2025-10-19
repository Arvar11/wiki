package com.panda.wiki.service;

import com.panda.wiki.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class WebSocketService {
    @Resource
    private WebSocketServer webSocketServer;

    @Async
    public void sendInfo(String message){
        log.info("异步方法执行，线程: {}", Thread.currentThread().getName());
        webSocketServer.sendInfo(message);
    }
}
