package com.boot.rabbitmq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * rabbitMq 连接
 */
public class ConnectionUtils {
    public static Connection getConnection() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        //地址 eoqbblaopsmobebj
        factory.setHost("127.0.0.1");
        //端口
        factory.setPort(5672);
        //v_host（类似数据库名）
        factory.setVirtualHost("/v_test");
        //用户名
        factory.setUsername("root");
        //密码
        factory.setPassword("root");
        return factory.newConnection();
    }
}
