package com.itcast.config;

import com.itcast.entity.User;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Map;

/**
 * 功能描述：客户端渠道拦截适配器
 * https://blog.csdn.net/qq_28988969/article/details/78134114
 *
 * @authro JIAQI
 * @date 2019/7/10 - 9:34
 */
@Component
public class WebSocketHandleInterceptor implements ChannelInterceptor {
    /**
     * 绑定user到websocket conn上
     * 获取包含在stomp中的用户信息
     *
     * @param message
     * @param channel
     * @return
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
//            String username = accessor.getFirstNativeHeader("username");
//            if (StringUtils.isEmpty(username)) {
//                return null;
//            }
////            // 绑定user
//            Principal principal = new UserPrincipal(username);
//            accessor.setUser(principal);

            Object raw = message.getHeaders().get(SimpMessageHeaderAccessor.NATIVE_HEADERS);
            if (raw instanceof Map) {
                Object name = ((Map) raw).get("name");
                if (name instanceof LinkedList) {
                    // 设置当前访问器的认证用户
                    accessor.setUser(new User(((LinkedList) name).get(0).toString()));
                }
            }
        }
        return message;
    }
}
