package com.itcast.rabbitmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 功能描述：MQ连接工具封装
 *
 * @author JIAQI
 * @date 2020/3/22 - 20:17
 */
public class MQConnectionUtils {

    private static final String userName = "boot";
    private static final String passWord = "123456";
    private static final String VirtualHost = "/boot";
    private static final String host = "127.0.0.1";
    private static final int port = 5672;

    public static Connection newConnection() throws IOException, TimeoutException {
        // 1.创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 2.设置用户名称
        factory.setUsername(userName);
        // 3.设置用户密码
        factory.setPassword(passWord);
        // 4.设置VirtualHost虚拟主机地址
        factory.setVirtualHost(VirtualHost);
        // 5.设置连接地址
        factory.setHost(host);
        // 6.设置amqp协议端口号
        factory.setPort(port);

        Connection connection = factory.newConnection();
        return connection;
    }
}
