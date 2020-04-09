package com.itcast.rabbitmq.boot;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 功能描述：
 *
 * @author JIAQI
 * @date 2020/4/5 - 16:40
 */
@Component
@RabbitListener(queues = "boot_email_queue_d")
public class EmailConsumer {

    @RabbitHandler
    public void process(String msg) {
        System.out.println("邮箱消费者接收消息:" + msg);
    }
}
