package com.itcast.producer.constant;

/**
 * 功能描述：MQ常量统一定义
 * (包含：队列、交换机、路由键)
 *
 * @author JIAQI
 * @date 2020/4/7 - 10:21
 */
public class MQConstant {

    // 死信队列
    public static final String DEAD_LETTERS_QUEUE = "dead.letters.queue";

    // 死信交换机
    public static final String DEAD_LETTERS_EXCHANGE = "dead.letters.exchange";

    // 邮件队列
    public static final String EMAIL_QUEUE = "boot.email.queue";

    // 邮件交换机
    public static final String EMAIL_EXCHANGE = "boot.email.exchange";

    // 邮件交换机路由键
    public static final String EMAIL_ROUTING_KEY = "boot.email.routing.key";

}
