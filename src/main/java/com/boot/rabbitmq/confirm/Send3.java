package com.boot.rabbitmq.confirm;

import com.boot.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

/**
 * rabbitmq confirm 模式
 *
 * 异步模式
 */
public class Send3 {
    public static final String QUEUE_NAEM = "text_queue_confirm";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        //声明通道
        channel.queueDeclare(QUEUE_NAEM,false,false,false,null);

        //confirm 模式 开启之后不能 开启事务
        channel.confirmSelect();
        //未确认标识
        SortedSet sortedSet = Collections.unmodifiableSortedSet(new TreeSet<Long>());

        channel.addConfirmListener(new ConfirmListener() {
            //没问题的通道
            @Override
            public void handleAck(long l, boolean b) throws IOException {
                if (b){
                    System.out.println("--handleAck--multiple");
                    sortedSet.headSet(l+1).clear();
                }else {
                    System.out.println("--handleAck--multiple-false");
                    sortedSet.remove(l);
                }
            }
            //存在问题的通道
            @Override
            public void handleNack(long l, boolean b) throws IOException {
                if (b){
                    System.out.println("--handleAck--multiple");
                    sortedSet.headSet(l+1).clear();
                }else {
                    System.out.println("--handleAck--multiple-false");
                    sortedSet.remove(l);
                }
            }
        });

        String msg = "msg: hello confirm";


        while (true){
            long seqNo = channel.getNextPublishSeqNo();
            channel.basicPublish("",QUEUE_NAEM,null,msg.getBytes());
            sortedSet.add(seqNo);
        }

    }
}
