package com.itcast.rabbitmq.boot;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 功能描述：发布订阅模式  配置
 *
 * @author JIAQI
 * @date 2020/4/5 - 16:08
 */
@Configuration
public class FanoutConfig {

    // 短信队列
    private String sms_queue = "boot_sms_queue_d";
    private String routing_sms = "boot_sms_routing";

    // 邮件队列
    private String email_queue = "boot_email_queue_d";
    private String routing_email = "boot_email_routing";

    // 队列名称
    private String exchange = "boot_exchange_d";

    // 1.定义队列
    @Bean
    public Queue fanoutSmsQueue() {
        return new Queue(sms_queue);
    }

    @Bean
    public Queue fanoutEmailQueue() {
        return new Queue(email_queue);
    }

    // 2.定义交换机
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(exchange);
    }

    // 3.队列和交换机进行绑定
    @Bean
    public Binding fanoutBindExchangeAndSmsQueue(Queue fanoutSmsQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(fanoutSmsQueue).to(directExchange).with(routing_sms);
    }

    @Bean
    public Binding fanoutBindExchangeAndEmailQueue(Queue fanoutEmailQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(fanoutEmailQueue).to(directExchange).with(routing_email);
    }

    @Autowired
    CachingConnectionFactory cachingConnectionFactory;

    RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            System.out.println();
        }
    };

    RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
        @Override
        public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {

        }
    };

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(cachingConnectionFactory);
        template.setConfirmCallback(confirmCallback);
        template.setReturnCallback(returnCallback);
        //        template.setConfirmCallback((data, ack, cause) -> {
//            if (ack) {
//                System.out.println("记录日志");
//            } else {
//                System.out.println("消息投递失败");
//            }
//        });
//        template.setReturnCallback((msg, repCode, repText, exchange, routingKey) -> {
//            System.out.println("消息投递失败");
//        });
        return template;
    }
}
