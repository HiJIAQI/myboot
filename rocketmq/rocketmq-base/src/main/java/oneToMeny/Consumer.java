package oneToMeny;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * 功能描述：消费者
 * 此次通过idea设置build and run 为可开启多个消费者
 *
 * @author JIAQI
 * @date 2022/7/24 - 19:53
 */
public class Consumer {

    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("GROUP_2");
        // 注册名称服务
        consumer.setNamesrvAddr("127.0.0.1:9876");
        // 设置消费模式（集群、广播 底层默认为集群，）
//        consumer.setMessageModel(MessageModel.BROADCASTING);
        // 设置订阅地址
        consumer.subscribe("TOPIC_2", "*");
        // 注册监听
//        consumer.registerMessageListener(new MessageListenerConcurrently() {
//            @Override
//            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messageExtList, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
//                messageExtList.forEach(messageExt -> {
//                    System.out.println(new String(messageExt.getBody()));
//                });
//                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//            }
//        });
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> messageExtList, ConsumeOrderlyContext consumeOrderlyContext) {
                messageExtList.forEach(messageExt -> {
                    System.out.println("消费了："+new String(messageExt.getBody()));
                });
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        consumer.start();
    }
}
