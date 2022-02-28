package com.itcast;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;

/**
 * 功能描述：kafka消费者
 * 自定义offset
 *
 * @author JIAQI
 * @date 2021/9/23 - 19:29
 */
public class kafkaCustomConsumer {

    private static Map<TopicPartition, Long> currentOffset = new HashMap<>();

    public static void main(String[] args) {
        Properties properties = new Properties();
        // kafka集群地址
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop1:9092");
        // 消费者组名称
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "myConsumer");
        // 是否开启自动提交 offset(相当于MQ中的ack) true:自动提交 false:手动提交
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        // 设置数据key的解码序列化处理类
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // 设置数据value的解码序列化处理类
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(properties);
        List<String> topics = new ArrayList<>();
        topics.add("first");
        kafkaConsumer.subscribe(topics, new ConsumerRebalanceListener() {
            // 该方法会在 Rebalance 之前调用
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> collection) {
                System.out.println("1");
                commitOffset(currentOffset);
            }

            // 该方法会在 Rebalance 之后调用
            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                System.out.println("2");
                currentOffset.clear();
                for (TopicPartition partition : partitions) {
                    // 定位到最近提交的 offset 位置继续消费
                    kafkaConsumer.seek(partition, getOffset(partition));
                }
            }
        });
        while (true) {
            // 消费者拉取数据
            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMinutes(1000));
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            }
            System.out.println("3");
            //异步提交
            commitOffset(currentOffset);
        }

    }

    // 获取某分区的最新 offset
    private static long getOffset(TopicPartition partition) {
        return 10080;
    }

    // 提交该消费者所有分区的 offset
    private static void commitOffset(Map<TopicPartition, Long> currentOffset) {
    }

}
