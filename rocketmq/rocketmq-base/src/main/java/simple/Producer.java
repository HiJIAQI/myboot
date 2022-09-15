package simple;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;

/**
 * 功能描述：生产者
 *
 * @author JIAQI
 * @date 2022/7/24 - 16:14
 */
public class Producer {
    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        // 1.消息由谁发送
        DefaultMQProducer mqProducer = new DefaultMQProducer("GROUP_1");
        // 2.消息发生到哪里
        mqProducer.setNamesrvAddr("127.0.0.1:9876");
        mqProducer.start();
        // 3.消息怎么发送
        String message = "你好，今天开始学习rocket MQ";
        Message sendMessage = new Message("TOPIC_1", message.getBytes(StandardCharsets.UTF_8));
        // 4.消息发生结果
        SendResult sendResult = mqProducer.send(sendMessage);
        System.out.println(sendResult.toString());
        // 5.结束消息发送
        mqProducer.shutdown();
    }
}
