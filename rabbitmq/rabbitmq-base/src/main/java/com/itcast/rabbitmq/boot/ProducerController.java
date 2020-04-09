package com.itcast.rabbitmq.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 功能描述：
 *
 * @author JIAQI
 * @date 2020/4/5 - 16:38
 */
@Controller
@RequestMapping("/producer")
public class ProducerController {

    @Autowired
    FanoutProducer fanoutProducer;

    @GetMapping
    @ResponseBody
    public String sendMsg(String queueName, String routingKey) {
        fanoutProducer.send(queueName, routingKey);
        return "SUCCESS";
    }
}
