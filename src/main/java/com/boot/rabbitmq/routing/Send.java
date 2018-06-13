package com.boot.rabbitmq.routing;

import com.boot.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 路由模式
 */
public class Send {

    private static final String EXCHANGE_NAME = "test_exchange_direct";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        //声明交换机 direct 处理消息
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");
        String msg = "hello direct !";
        String routingKey = "info";
        channel.basicPublish(EXCHANGE_NAME,routingKey,null,msg.getBytes());
        channel.close();
        connection.close();
    }

}
