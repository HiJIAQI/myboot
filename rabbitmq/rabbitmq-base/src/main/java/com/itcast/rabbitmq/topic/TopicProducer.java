package com.itcast.rabbitmq.topic;

import com.itcast.rabbitmq.MQConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 功能描述：发布订阅生产者
 * 绑定交换机 路由
 *
 * @author JIAQI
 * @date 2020/3/30 - 22:55
 */
public class TopicProducer {
    // 交换机
    private static final String EXCHANGE_NAME = "topic_exchange";
    // 路由
    private static final String ROUTING_KEY = "log.info";

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("生产者已启动....");
        // 1.建立连接
        Connection connection = MQConnectionUtils.newConnection();
        // 2.创建通道
        Channel channel = connection.createChannel();
        // 3.绑定交换机(exchange:交换机名称   type：类型 topic)
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        // 4.发送消息
        String msg = "生产者发送了消息topic";
        //exchange,routingKey,BasicProperties props, byte[] body
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, msg.getBytes());
        // 5.关闭通道和连接
        channel.close();
        connection.close();
    }
}
