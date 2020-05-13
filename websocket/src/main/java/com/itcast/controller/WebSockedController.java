package com.itcast.controller;

import com.itcast.util.RequestMessage;
import com.itcast.util.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 功能描述：
 *
 * @authro JIAQI
 * @date 2019/7/8 - 17:29
 */
@Controller
public class WebSockedController {

    /**
     * 广播推送
     * <p>
     * MessageMapping 指定要接收消息的地址，类似@RequestMapping。除了注解到方法上，也可以注解到类上
     * SendTo默认 消息将被发送到与传入消息相同的目的地
     */
    @MessageMapping("/sendTest")
    @SendTo("/topic/subscribeTest")
    public ResponseMessage broadcast(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setResponseMessage("你发送的消息为:" + requestMessage.getName());
        return responseMessage;
    }

    /**
     * 精确推送到指定用户
     * SendToUser 发送给指定用户
     */
    @MessageMapping("/sendUser")
    @SendToUser(value = "/queue/subscribeUser", broadcast = false)
    public ResponseMessage broadcastToUser(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setResponseMessage("你发送的消息为:" + requestMessage.getName());
        return responseMessage;
    }

    /**
     * SubscribeMapping接收客户端发送的订阅
     */
    @SubscribeMapping("/subscribeTest")
    public ResponseMessage subscribe() {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setResponseMessage("感谢你订阅了我");
        return responseMessage;
    }

    //spring提供的发送消息模板
    @Autowired
    SimpMessagingTemplate template;

    @GetMapping("/topic")
    @ResponseBody
    public String gg() {
        template.convertAndSend("/topic/getResponse", new ResponseMessage("通过SimpMessagingTemplate进行消息广播该方式无法直接被使用，需要建立连接后访问使用,"));
        return "广播推送，所有用户都收得到";
    }

    /**
     * 指定用户发送消息页
     */
    @RequestMapping(value = "/goTopic")
    public String goTopic(HttpServletRequest req) {
        return "/topic";
    }

    /**
     * 指定用户发送消息页
     */
    @RequestMapping(value = "/goQueue")
    public String goQueue(HttpServletRequest req) {
        return "/queue";
    }

    /**
     * 根据发送消息给指定用户
     */
    @RequestMapping(value = "/queueToUser")
    @ResponseBody
    public String sendMsqToUserTest() {
        //发送消息给指定用户
        template.convertAndSendToUser("test1", "/queue/message", new ResponseMessage("服务器主动推的数据"));
        return "精确推送,只推送到test1用户";
    }
}


