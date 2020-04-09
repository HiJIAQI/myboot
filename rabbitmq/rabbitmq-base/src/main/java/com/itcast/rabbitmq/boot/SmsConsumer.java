package com.itcast.rabbitmq.boot;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 功能描述：
 *
 * @author JIAQI
 * @date 2020/4/5 - 16:40
 */
@Component
@RabbitListener(queues = "boot_sms_queue_d")
public class SmsConsumer {

    @RabbitHandler
    public void process(Message message, @Headers Map<String, Object> headers, Channel channel) throws Exception {
        //防止重复提交(消息幂等性)
        String messageId = message.getMessageProperties().getMessageId();
        String msg = new String(message.getBody(), "UTF-8");
        System.out.println("短信消费者接收消息:" + msg);

        //手动ACK
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        //手动签收
        channel.basicAck(deliveryTag, false);
    }
}
