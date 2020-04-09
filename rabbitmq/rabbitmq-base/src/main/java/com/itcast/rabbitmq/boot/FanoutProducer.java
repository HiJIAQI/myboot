package com.itcast.rabbitmq.boot;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

/**
 * 功能描述：
 *
 * @author JIAQI
 * @date 2020/4/5 - 16:28
 */
@Component
public class FanoutProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String queueName, String routingKey) {
        System.out.println("生产者发送消息....");
        String msg = "你好BOOT" + LocalDate.now();
        // 设置消息唯一id 保证每次重试消息id唯一
        Message message = MessageBuilder.withBody(msg.getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setContentEncoding("utf-8")
                .setMessageId(UUID.randomUUID() + "").build();

        amqpTemplate.convertAndSend("boot_exchange_d", routingKey, message);
    }

}
