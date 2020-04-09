package com.itcast.rabbitmq.routing;

import com.itcast.rabbitmq.MQConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 功能描述：短信消费者
 *
 * @author JIAQI
 * @date 2020/3/30 - 23:06
 */
public class SmsConsumer {
    // 队列名称
    private static final String SMS_QUEUE_NAME = "sms_queue";
    // 交换机
    private static final String EXCHANGE_NAME = "routing_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("短信消费者启动....");
        // 1.建立连接
        Connection connection = MQConnectionUtils.newConnection();
        // 2.创建通道
        Channel channel = connection.createChannel();
        // 3.声明队列
        channel.queueDeclare(SMS_QUEUE_NAME, false, false, false, null);
        // 4.消费者队列绑定交换机
        channel.queueBind(SMS_QUEUE_NAME,EXCHANGE_NAME,"sms");
        channel.queueBind(SMS_QUEUE_NAME,EXCHANGE_NAME,"email");

        // 5.监听消息
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                String msg = new String(body, "UTF-8");
                System.out.println(msg);
            }
        };
        // 6.应答
        channel.basicConsume(SMS_QUEUE_NAME,true,defaultConsumer);
    }
}
