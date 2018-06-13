package com.boot.rabbitmq;

import com.boot.rabbitmq.config.Sender;
import javafx.application.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.Date;
@RunWith(value=SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RabbitMqTest {
    @Autowired
    private Sender sender;

    @Test
    public void send () throws InterruptedException {
        while (true) {
            String msg = new Date().toString();
            sender.send(msg);
            Thread.sleep(1000);
        }

    }
}
