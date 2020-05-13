package com.itcast.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * 通过EnableWebSocketMessageBroker
 * 开启使用STOMP协议来传输基于代理(message broker)的消息,
 * 此时浏览器支持使用@MessageMapping 就像支持@RequestMapping一样。
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    WebSocketHandleInterceptor webSocketHandleInterceptor;

    /**
     * 注册STOMP端点
     *
     * @param registry STOMP端点注册器
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) { //endPoint 注册协议节点,并映射指定的URl
        // 点对点-用
        // 注册一个名字为"endpointChat" 的endpoint,并指定 SockJS协议。
        // 允许使用socketJs方式访问，访问点为webSocketServer，允许跨域
        // 在网页上我们就可以通过这个链接
        // http://localhost:8888/endpointChat
        // 来和服务器的WebSocket连接
        registry.addEndpoint("/endpointChat")
                .setAllowedOrigins("*")
                .withSockJS();
    }

    /**
     * 配置消息代理(message broker)
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {//配置消息代理(message broker)
        // 订阅Broker名称:topic 代表发布广播，即群发
        // queue 代表点对点，即发指定用户
        registry.enableSimpleBroker("/queue", "/topic");
        // 全局使用的消息前缀（客户端订阅路径上会体现出来）
        registry.setApplicationDestinationPrefixes("/app");
        // 点对点使用的订阅前缀（客户端订阅路径上会体现出来），
        // 不设置的话，默认也是/user/
        // registry.setUserDestinationPrefix("/user/");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        // 注册了一个接受客户端消息通道拦截器
        registration.interceptors(webSocketHandleInterceptor);
    }

}
