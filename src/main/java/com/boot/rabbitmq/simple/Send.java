package com.boot.rabbitmq.simple;

import com.boot.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 生产者--简单队列 -耦合性高
 */
public class Send {
    public static final String QUEUE_NAME = "test_simple";
    public static void main(String[] args) throws IOException, TimeoutException {
        //拿到连接
        Connection connection = ConnectionUtils.getConnection();
        //拿到通道
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //消息
        String msg = "hello simple !";
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
        System.out.println("success");
        channel.close();
        connection.close();
    }
}
