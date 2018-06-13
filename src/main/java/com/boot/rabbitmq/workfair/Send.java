package com.boot.rabbitmq.workfair;

import com.boot.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * work_queue 工作队列 --- 轮询分发
 *
 * boolean autoAck = true;//自动应答
 * (自动确认模式)一旦rabbitmq 将消息分发给消费者 就会从内存中删除
 * 这种情况下 如果杀死正在执行的消费者就会丢失数据
 * 解决
 *  boolean autoAck = false;//手动应答
 *
 */
public class Send {
    public static final String QUEUE_NAME = "test_work_queue_fair";

    public static void main(String[] args) throws IOException, TimeoutException {
        //拿到链接
        Connection connection = ConnectionUtils.getConnection();
        //拿到通道
        Channel channel = connection.createChannel();
        //声明队列 第一个boolean 是否持久化

        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        /**
         * 一次只发一条数据给消费者 限制发送同一个消费者不得超过一条消息
         */
        int idex = 1;
        channel.basicQos(1);

        for (int i = 0; i < 50; i++){
            String msg = "[ WQ ] :  " + i;
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            System.out.println("[send ]: " + msg);
            try {
                Thread.sleep(i*30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        channel.close();
        connection.close();

    }

}
