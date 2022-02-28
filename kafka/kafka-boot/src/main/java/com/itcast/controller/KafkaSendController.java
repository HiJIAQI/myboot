package com.itcast.controller;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 功能描述：
 *
 * @author JIAQI
 * @date 2021/9/24 - 20:02
 */
@Controller
public class KafkaSendController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    //发送消息方法
    @GetMapping("/send")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public String send(@RequestParam String msg) {
        ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send("first", msg);
        listenableFuture.addCallback(success -> {
            // 消息发送到的topic
            if (success != null) {
                String topic = success.getRecordMetadata().topic();
                // 消息发送到的分区
                int partition = success.getRecordMetadata().partition();
                // 消息在分区内的offset
                long offset = success.getRecordMetadata().offset();
                System.out.println("发送消息成功:" + topic + "-" + partition + "-" + offset);
            }
        }, failure -> {
            System.out.println("发送消息失败:" + failure.getMessage());
        });
        return "发送成功";
    }

    //发送消息方法
    @GetMapping("/sendTx")
    @ResponseBody
    public String sendTx(@RequestParam String msg) {
        kafkaTemplate.executeInTransaction((KafkaOperations.OperationsCallback<String, String, Object>) kafkaOperations -> {
            kafkaOperations.send("first", msg);
            return true;
        });
        return "事务发送成功";
    }
}
