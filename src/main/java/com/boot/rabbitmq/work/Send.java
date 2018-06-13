package com.boot.rabbitmq.work;

import com.boot.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * work_queue 工作队列 --- 轮询分发
 */
public class Send {
    public static final String QUEUE_NAME = "test_work_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //拿到链接
        Connection connection = ConnectionUtils.getConnection();
        //拿到通道
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        for (int i = 0; i < 50; i++){
            String msg = "[ WQ ] :  " + i;
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            System.out.println("[send ]: " + msg);
            try {
                Thread.sleep(i*20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        channel.close();
        connection.close();

    }

}
