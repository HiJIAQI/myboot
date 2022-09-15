package oneToMeny;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 功能描述：生产者
 *
 * @author JIAQI
 * @date 2022/7/24 - 19:52
 */
public class Producer {

    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("GROUP_1");
        // 注册名称服务
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();
        // 构建需要发送的消息对象
        for (int i = 0; i < 10; i++) {
            String message = "测试消息发送" + i;
            Message sendMessage = new Message("TOPIC_2", message.getBytes(StandardCharsets.UTF_8));
            SendResult sendResult = producer.send(sendMessage, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                    return list.get(0);
                }
            }, null);
            System.out.println(sendResult.toString());
        }
        // 结束消息发送
        producer.shutdown();
    }
}
