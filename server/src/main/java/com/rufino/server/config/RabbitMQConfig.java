package com.rufino.server.config;

import com.rufino.server.services.RabbitMqService;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

// @Profile({ "config", "work-queues" })
@Configuration
public class RabbitMQConfig {

    @Value("${sample.rabbitmq.queue}")
    String queueName;

    @Bean
    public Queue hello() {
        return new Queue(queueName);
    }


    @Profile("sender")
    @Bean
    public RabbitMqService sender() {
        return new RabbitMqService();
    }
}
