package com.itcast.consumer.receiver;

import com.itcast.producer.constant.MQConstant;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能描述：消费者消息接收
 *
 * @author JIAQI
 * @date 2020/4/7 - 13:46
 */
@Component //（实例化到spring容器中，相当于配置文件中的<bean id="" class=""/>）
public class EmailReceiver {

    private static final Logger logger = LoggerFactory.getLogger(EmailReceiver.class);

    /**
     * rabbitmq 默认情况下 如果消费者程序出现异常的情况下，会自动实现补偿机制(例如 int i = 1 / 0)
     * 补偿（重试机制） 队列服务器 发送补偿请求
     *
     * @RabbitListener 底层 使用Aop进行拦截，如果程序没有抛出异常，自动提交事务
     * 如果Aop使用异常通知拦截 获取异常信息的话，自动实现补偿机制 ，该消息会缓存到rabbitmq服务器端进行存放，一直重试到不抛异常为准。
     */

//    @RabbitListener(queues = MQConstant.EMAIL_QUEUE)
//    public void emailHandle(Message message, Channel channel) throws Exception {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println("消息接收时间:" + sdf.format(new Date()));
//
//        String messageId = message.getMessageProperties().getMessageId();
//
//        String msg = new String(message.getBody(), "UTF-8");
//
//        Long deliveryTag = message.getMessageProperties().getDeliveryTag();
//
//        System.out.println("消费者收到消息：" + msg);
//        //进行手动应答
//        channel.basicAck(deliveryTag, false);
//        //重回队列
//        //channel.basicNack(deliveryTag, false, true);
//    }
    @RabbitListener(queues = MQConstant.DEAD_LETTERS_QUEUE)
    public void deadHandler(Message message, Channel channel) throws IOException {
        /**
         * 发送消息之前，根据订单ID去查询订单的状态，如果已支付不处理，如果未支付，则更新订单状态为取消状态。
         */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("死信队列消息接收时间:" + sdf.format(new Date()));
        // 从队列中取出订单号
        String orderId = new String(message.getBody(), StandardCharsets.UTF_8);
        String messageId = message.getMessageProperties().getMessageId();
        logger.info("消费者接收到订单：" + message.getMessageProperties().getMessageId());
        if ("1".equals(messageId)) {
            //取消订单
            logger.info("订单已支付");
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            return;
        }
        //手动应答
        logger.info("订单已取消");
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}
