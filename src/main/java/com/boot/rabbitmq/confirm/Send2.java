package com.boot.rabbitmq.confirm;

import com.boot.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * rabbitmq confirm 模式
 *
 * 批量模式
 */
public class Send2 {
    public static final String QUEUE_NAEM = "text_queue_confirm";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        //声明通道
        channel.queueDeclare(QUEUE_NAEM,false,false,false,null);

        //confirm 模式 开启之后不能 开启事务
        channel.confirmSelect();
        String msg = "msg: hello confirm";

        for (int i = 0;i < 10; i++){
            channel.basicPublish("",QUEUE_NAEM,null,msg.getBytes());
        }
        //批量
        if (!channel.waitForConfirms()){
            System.out.println("msg send failed");
        }else {
            System.out.println("msg send ok");
        }
        channel.close();
        connection.close();
    }
}
