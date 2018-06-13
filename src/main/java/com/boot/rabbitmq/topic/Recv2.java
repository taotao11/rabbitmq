package com.boot.rabbitmq.topic;

import com.boot.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv2 {
    public static final String QUEUE_NAME = "test_queue_topic_2";
    public static final String CHANGE_NAME = "test_change_topic_1";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        //拿到通道
        Channel channel = connection.createChannel();

        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //绑定到交换机传换器 主题全匹配模式
        channel.queueBind(QUEUE_NAME,CHANGE_NAME,"good.#");
        channel.basicQos(1);//一次只分发一个
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                System.out.println("[2]" + msg);
                try {
                    Thread.sleep(300);
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
