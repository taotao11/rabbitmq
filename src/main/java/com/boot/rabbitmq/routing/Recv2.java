package com.boot.rabbitmq.routing;

import com.boot.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv2 {
    public static final String QUEUE_NAME = "test_exchange_direct_2";
    public static final String CHANGE_NAME = "test_exchange_direct";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        //拿到通道
        final  Channel channel = connection.createChannel();

        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //绑定到交换机传换器
        channel.queueBind(QUEUE_NAME,CHANGE_NAME,"error");
        channel.queueBind(QUEUE_NAME,CHANGE_NAME,"info");
        channel.queueBind(QUEUE_NAME,CHANGE_NAME,"warning");
        channel.basicQos(1);//一次只分发一个
        
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                try {
                    Thread.sleep(300);
                    System.out.println("[2] msg : " + msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {

                    System.out.println("[2] done ");
                    //手动回执消息
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };
        boolean autoAck = false;//自动应答
        //监听队列
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);
    }
}
