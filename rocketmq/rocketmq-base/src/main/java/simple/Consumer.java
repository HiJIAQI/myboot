package simple;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 功能描述：消费者
 *
 * @author JIAQI
 * @date 2022/7/24 - 16:14
 */
public class Consumer {
    public static void main(String[] args) throws MQClientException {
        // 1.谁来接收消息
        DefaultMQPushConsumer pushConsumer = new DefaultMQPushConsumer("GROUP_1");
        // 2.从哪里接收消息
        pushConsumer.setNamesrvAddr("127.0.0.1:9876");
        // 3.监听哪个消息队列
        pushConsumer.subscribe("TOPIC_1", "*");
        // 4.注册监听
        pushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messageExtList, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                messageExtList.forEach(messageExt -> {
                    String msg = new String(messageExt.getBody());
                    System.out.println("消费者获取到的消息:" + msg);
                });
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        pushConsumer.start();
    }
}
