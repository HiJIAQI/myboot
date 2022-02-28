package com.itcast;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * 功能描述：
 *
 * @author JIAQI
 * @date 2021/9/24 - 20:05
 */
@Component
public class KafkaConsumerListener {

    // 新建一个异常处理器，用@Bean注入
    @Bean
    public ConsumerAwareListenerErrorHandler consumerAwareErrorHandler() {
        return (message, exception, consumer) -> {
            System.out.println("消费异常：" + message.getPayload());
            return null;
        };
    }

    // 将这个异常处理器的BeanName放到@KafkaListener注解的errorHandler属性里面
    @KafkaListener(topics = "second", groupId = "myConsume1r", errorHandler = "consumerAwareErrorHandler")
    public void onMessage4(ConsumerRecord<String, String> record) throws Exception {
        throw new Exception("简单消费-模拟异常");
    }

//    @KafkaListener(topics = "first", groupId = "myConsumer", errorHandler = "consumerAwareErrorHandler")
//    public void receiver(List<ConsumerRecord<String, String>> records, Acknowledgment acknowledgment) {
//        records.forEach(record -> {
//            System.out.printf("topic = %s, offset = %d, value = %s \n", record.topic(), record.offset(), record.value());
//        });
//        // 进行手动提交
//        acknowledgment.acknowledge();
//    }

    @KafkaListener(topics = "first", groupId = "myConsumer", errorHandler = "consumerAwareErrorHandler")
    public void receiver(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        System.out.printf("topic = %s, offset = %d, value = %s \n", record.topic(), record.offset(), record.value());
        // 进行手动提交
        acknowledgment.acknowledge();
    }
}
