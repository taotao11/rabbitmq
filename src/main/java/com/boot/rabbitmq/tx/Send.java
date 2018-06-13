package com.boot.rabbitmq.tx;

import com.boot.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者
 */
public class Send {

    public static final String QUEUE_NAEM = "test_queue_tx";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        //声明通道
        channel.queueDeclare(QUEUE_NAEM,false,false,false,null);

        //发消息
        String msg = "hello tx";

        try {
            //开启事务
            channel.txSelect();
            channel.basicPublish("",QUEUE_NAEM,null,msg.getBytes());
            int i = 1/0;
            //提交事务
            channel.txCommit();
        }catch (Exception e){
            //回滚
            channel.txRollback();
            System.out.println("channel rollback");
        }finally {
            channel.close();
            connection.close();
        }

    }
}
