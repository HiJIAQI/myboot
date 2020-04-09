package com.itcast.rabbitmq.eazy;

import com.itcast.rabbitmq.MQConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 功能描述：MQ生产者(即消息发送端)
 *
 * @author JIAQI
 * @date 2020/3/22 - 20:41
 */
public class Producer {
    // 队列名称
    private static final String QUEUE_NAME = "testBoot";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1.创建一个新的连接
        Connection connection = MQConnectionUtils.newConnection();
        // 2.创建一个通道(可以使用该通道发送和接收消息)
        Channel channel = connection.createChannel();
        // 3.创建一个队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        try {
            // 4.创建msg
            String msg = "这是一个简单队列";
            System.out.println("生产者发送消息:" + msg);
            // 开启事务
            channel.txSelect();
            // 5.生产者发送消息者
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
//            int i = 1 / 0;
            // 提交事务
            channel.txCommit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            //事务回滚
            channel.txRollback();
        } finally {
            // 关闭通道和连接
            channel.close();
            connection.close();
        }
    }
}
