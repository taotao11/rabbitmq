package com.boot.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 注解为配置信息 启动时加载
 */
@Configuration
public class RabbitMqConfig {
    //声明队列
    @Bean
    public Queue queue(){
        return new Queue("test_springboot",true); // true表示持久化该队列
    }

    /**
     * 声明交换机
     * @return
     */
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange("topicExchange");
    }

    /**
     * 交换机绑定队列
     * @return
     */
    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue()).to(topicExchange()).with("key.#");
    }
}
