package com.itcast.producer.config;

import com.itcast.producer.constant.MQConstant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述：mq配置类
 *
 * @author JIAQI
 * @date 2020/4/7 - 10:16
 */
@Configuration
@ComponentScan
public class RabbitMqConfig {

//    方法一：Confirm确认机制实现
//    @Autowired
//    CachingConnectionFactory cachingConnectionFactory;
//
//    @Bean
//    public RabbitTemplate rabbitTemplate() {
//        RabbitTemplate template = new RabbitTemplate(cachingConnectionFactory);
//        template.setConfirmCallback((correlationData, ack, cause) -> {
//            System.out.println(correlationData.getId());
//            System.out.println(ack);
//            System.out.println(cause);
//
//        });
//        template.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
//            System.err.println("消息发送失败");
//        });
//        return template;
//    }

    /**
     * 声明死信队列、死信交换机、绑定队列到死信交换机
     * 建议使用FanoutExchange广播式交换机
     */
    @Bean
    public Queue deadLettersQueue() {
        return new Queue(MQConstant.DEAD_LETTERS_QUEUE);
    }

    @Bean
    public FanoutExchange deadLettersExchange() {
        return new FanoutExchange(MQConstant.DEAD_LETTERS_EXCHANGE);
    }

    @Bean
    public Binding deadLettersBinding() {
        return BindingBuilder.bind(deadLettersQueue()).to(deadLettersExchange());
    }

    // 1.定义队列(此处无需进行额外设置持久化,在底层方法中已默认为true)
    // 声明普通队列，并指定相应的备份交换机、死信交换机
    @Bean
    public Queue emailQueue() {
        Map<String, Object> arguments = new HashMap<>(10);
        //指定死信发送的Exchange
        arguments.put("x-dead-letter-exchange", MQConstant.DEAD_LETTERS_EXCHANGE);
        return new Queue(MQConstant.EMAIL_QUEUE, true, false, false, arguments);
    }

    // 2.定义交换机(此处无需进行额外设置持久化,在底层方法中已默认为true)
    @Bean
    public DirectExchange emailExchange() {
        return new DirectExchange(MQConstant.EMAIL_EXCHANGE);
    }

    // 3.队列和交换机进行绑定
    @Bean
    public Binding bindEmailQueueAndExchange(Queue emailQueue, DirectExchange emailExchange) {
        return BindingBuilder.bind(emailQueue).to(emailExchange).with(MQConstant.EMAIL_ROUTING_KEY);
    }

}
