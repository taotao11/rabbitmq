package com.boot.rabbitmq.topic;

import com.boot.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 主题模式
 */
public class Send {
    public static final String CHANGE_NAME = "test_change_topic_1";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获得连接
        Connection connection = ConnectionUtils.getConnection();
        //获得通道
        Channel channel = connection.createChannel();
        //声明交换机
        channel.exchangeDeclare(CHANGE_NAME,"topic");//分发
        String msg = "商品.....";
        //发送消息
        channel.basicPublish(CHANGE_NAME,"good.update",null,msg.getBytes());
        System.out.println("[topic:]" + msg);
        channel.close();
        connection.close();

    }

}
