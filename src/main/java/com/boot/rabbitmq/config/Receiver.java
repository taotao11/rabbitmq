package com.boot.rabbitmq.config;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消费者
 *
 * 需要配置监听
 * @Component 用于不清楚是什么类型的类 与 @Service 相似
 */
@Component
public class Receiver {

    @RabbitListener(queues = "test_springboot")
    public String processMessage(String msg){
        System.out.println(Thread.currentThread().getName() + " 接收到来自test_springboot队列的消息：" + msg);
        return msg.toUpperCase();
    }
}
