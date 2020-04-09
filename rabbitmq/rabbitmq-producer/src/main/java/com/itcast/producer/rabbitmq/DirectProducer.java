package com.itcast.producer.rabbitmq;

import com.itcast.producer.constant.MQConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能描述：生产者端
 *
 * @author JIAQI
 * @date 2020/4/7 - 11:00
 */
@Component
public class DirectProducer implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    private static final Logger logger = LoggerFactory.getLogger(DirectProducer.class);

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void sendEmail() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("消息发送时间:" + sdf.format(new Date()));

        String msg = "HELLOW WORD";
        // 死信队列  设置过期时间(30秒过期)
        int expiration = 1000 * 30;

        System.out.println("生产者启动发送消息：" + msg);

        /**
         * 开启强制委托
         * 当mandatory标志位设置为true时
         * 如果exchange根据自身类型和消息routingKey无法找到一个合适的queue存储消息
         * 那么broker会调用basic.return方法将消息返还给生产者
         * 当mandatory设置为false时，出现上述情况broker会直接将消息丢弃
         */

        this.rabbitTemplate.setMandatory(true);
        this.rabbitTemplate.setConfirmCallback(this);
        this.rabbitTemplate.setReturnCallback(this);
        // 使用单独的发送连接，避免生产者由于各种原因阻塞而导致消费者同样阻塞
        this.rabbitTemplate.setUsePublisherConnection(true);
        for (int i = 0; i < 8; i++) {
            // 构建消息实体
            Message message = MessageBuilder.withBody(msg.getBytes())
                    .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                    .setContentEncoding("utf-8")
                    .setExpiration(String.valueOf(expiration))
                    .setMessageId(i + "")
                    .build();

            System.out.println(i);
            rabbitTemplate.convertAndSend(MQConstant.EMAIL_EXCHANGE, MQConstant.EMAIL_ROUTING_KEY, message, new CorrelationData(String.valueOf(i)));
        }
    }

    /**
     * 如果消息没有到达交换机,则该方法中ack = false,cause;
     * 如果消息正确到达交换机,则该方法中ack = true;
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        logger.info("confirm回调方法>>>回调消息ID为: " + correlationData.getId());
        if (ack) {
            logger.info("confirm回调方法>>>消息发送到交换机成功！");
        } else {
            logger.error("confirm回调方法>>>消息发送到交换机失败！，原因 : [{}]", cause);
        }
    }

    /**
     * 消息从交换机成功到达队列，则returnedMessage方法不会执行;
     * 消息从交换机未能成功到达队列，则returnedMessage方法会执行;
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        logger.error("returnedMessage回调方法>>>" + new String(message.getBody(), StandardCharsets.UTF_8) + ",replyCode:" + replyCode
                + ",replyText:" + replyText + ",exchange:" + exchange + ",routingKey:" + routingKey);
    }
}
