package com.itcast.rabbitmq.eazy;

import com.itcast.rabbitmq.MQConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 功能描述：MQ消费者(即消息接收端)
 *
 * @author JIAQI
 * @date 2020/3/22 - 21:06
 */
public class Consumer {
    // 队列名称
    private static final String QUEUE_NAME = "testBoot";

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("消费者启动....");
        // 1.创建一个新的连接
        Connection connection = MQConnectionUtils.newConnection();
        // 2.创建一个通道
        Channel channel = connection.createChannel();
        // 3..消费者关联队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            // 监听获取消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                String msg = new String(body, "UTF-8");
                System.out.println("消费者获取生产者消息:" + msg);
            }
        };
        // 4.设置应答模式 如果为true情况下 表示为自动应答模式 false 表示为手动应答
        channel.basicConsume(QUEUE_NAME, true, defaultConsumer);
    }
}
