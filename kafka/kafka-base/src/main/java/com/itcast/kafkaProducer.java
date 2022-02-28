package com.itcast;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * 功能描述：kafka生产者
 *
 * @author JIAQI
 * @date 2021/9/23 - 19:28
 */
public class kafkaProducer {

    public static void main(String[] args) {
        Properties properties = new Properties();
        // kafka集群地址
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop1:9092");
        // 重试机制(0,1,-1(all))
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        // 重试次数
        properties.put(ProducerConfig.RETRIES_CONFIG, 1);
        // 批次大小kb(默认)
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        // 等待时间ms(默认)
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        // RecordAccumulator 缓冲区大小kb(默认)
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        // 设置数据key的编码序列化处理类
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // 设置数据value的编码序列化处理类
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // jdk1.7争强try语句，try块退出时，会自动调用res.close()方法，关闭资源。不用写一大堆finally来关闭资源
        try (KafkaProducer<String, String> producer = new KafkaProducer<>(properties)) {
            // 创建生产者对象
            for (int i = 0; i < 10; i++) {
                // 发送消息到kafka
                producer.send(new ProducerRecord<>("first", "hello-" + i), (recordMetadata, exception) -> {
                    if (exception == null) {
                        System.out.println(recordMetadata.topic() + "存储分区->" + recordMetadata.partition() + "   偏移量->" +
                                recordMetadata.offset());
                    } else {
                        exception.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
