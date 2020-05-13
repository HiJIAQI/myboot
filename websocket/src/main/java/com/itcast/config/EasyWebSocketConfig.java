package com.itcast.config;

import com.itcast.handler.MyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * 功能描述：基础websocket测试demo配置
 *
 * @author JIAQI
 * @date 2020/5/13 - 13:57
 */
@Configuration
@EnableWebSocket
public class EasyWebSocketConfig implements WebSocketConfigurer {

    @Autowired
    MyHandler myHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        // 添加头部信息  并设置可跨域
        webSocketHandlerRegistry
                .addHandler(this.myHandler, "/ws")
                .setAllowedOrigins("*");
    }
}
