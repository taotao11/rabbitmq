package com.boot.rabbitmq.ps;

import com.boot.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 发布订阅模式
 */
public class Send {
    public static final String CHANGE_NAME = "test_change";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获得连接
        Connection connection = ConnectionUtils.getConnection();
        //获得通道
        Channel channel = connection.createChannel();
        //声明交换机
        channel.exchangeDeclare(CHANGE_NAME,"fanout");//分发
        String msg = "hello ps";
        //发送消息
        channel.basicPublish(CHANGE_NAME,"",null,msg.getBytes());
        System.out.println("[ps:]" + msg);
        channel.close();
        connection.close();

    }

}
