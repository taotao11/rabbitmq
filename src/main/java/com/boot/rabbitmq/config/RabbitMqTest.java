package com.boot.rabbitmq.config;
import java.util.Date;
public class RabbitMqTest {
    public void send () throws InterruptedException {

    }

    public static void main(String[] args) {
        Sender sender = new Sender();
        while (true) {
            String msg = new Date().toString();
            sender.send(msg);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
