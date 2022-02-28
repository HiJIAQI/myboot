package com.itcast;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 功能描述：kafka消费者
 *
 * @author JIAQI
 * @date 2021/9/23 - 19:29
 */
public class kafkaConsumer {
    // KafkaConsumer：需要创建一个消费者对象，用来消费数据
    // ConsumerConfig：获取所需的一系列配置参数
    // ConsumerRecord：每条数据都要封装成一个 ConsumerRecord 对象为了使我们能够专注于自己的业务逻辑，Kafka 提供了自动提交 offset 的功能。
    // 自动提交 offset 的相关参数：
    // enable.auto.commit：是否开启自动提交 offset 功能
    // auto.commit.interval.ms：自动提交 offset 的时间间隔
    public static void main(String[] args) {
        Properties properties = new Properties();
        // kafka集群地址
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop1:9092");
        // 消费者组名称
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "myConsumer");
        // 是否开启自动提交 offset(相当于MQ中的ack) true:自动提交 false:手动提交
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        // 自动提交 offset 的时间间隔(ms)
//        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        // 设置数据key的解码序列化处理类
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // 设置数据value的解码序列化处理类
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(properties);
        List<String> topics = new ArrayList<>();
        topics.add("first");
        kafkaConsumer.subscribe(topics);
        while (true) {
            // 消费者拉取数据
            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMinutes(1000));
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            }
            // 同步提交,当前线程会阻塞直到 offset 提交成功,先消费后提交 offset，有可能会造成数据的重复消费
            // kafkaConsumer.commitSync();
            // 异步提交,先提交 offset后消费，可能造成数据的漏消费
            kafkaConsumer.commitAsync((offsets, exception) -> {
                if (exception != null) {
                    System.err.println("Commit failed for" + offsets);
                } else {
                    System.out.println("offsets:" + offsets);
                }
            });
        }
    }
}
