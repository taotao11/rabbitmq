package com.boot.rabbitmq.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

/**
 * 生产者
 * @Component 用于不清楚是什么类型的类 与 @Service 相似
 */
@Component
public class Sender implements RabbitTemplate.ConfirmCallback,ReturnCallback {
    @Autowired
    private RabbitTemplate template;

    @PostConstruct
    public void init() {
        template.setConfirmCallback(this);
        template.setReturnCallback(this);
    }

    /**
     * 消息发送的回调
     * @param correlationData
     * @param b
     * @param s
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        if (b){
            System.out.println("消息发送成功" + correlationData);
        }else {
            System.out.println("消息发送失败" + s);
        }
    }

    /**
     * 消息发送失败的回调
     * @param message
     * @param i
     * @param s
     * @param s1
     * @param s2
     */
    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        System.out.println(message.getMessageProperties().getCorrelationIdString() + "发送失败");
    }

    public void send(String message){
        //定义index
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        System.out.println("开始发送消息: " + message.toLowerCase());
        String response = template.convertSendAndReceive("topicExchange","key.1",message,correlationData).toString();
        System.out.println("结束发消息: " + message.toLowerCase());
        System.out.println("消费者响应: " + response + " 消息处理完成");
    }
}
