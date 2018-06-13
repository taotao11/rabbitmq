package com.boot.rabbitmq.confirm;

import com.boot.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv {
    public static final String QUEUE_NAEM = "text_queue_confirm";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        //声明通道
        channel.queueDeclare(QUEUE_NAEM,false,false,false,null);

        channel.basicConsume(QUEUE_NAEM,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("[recv] : " + new String(body,"utf-8"));
            }
        });

    }
}
