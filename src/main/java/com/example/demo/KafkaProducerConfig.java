package com.example.demo;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.kafka.outbound.KafkaProducerMessageHandler;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sudhiry on 1/20/18.
 */

@Configuration
public class KafkaProducerConfig {


    private String bootstrapServers = "localhost:9092";

    @Bean
    public DirectChannel toKafka(){
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "toKafka")
    public MessageHandler handle(){
        KafkaProducerMessageHandler<String,Object> kafkaProducerMessageHandler =
                new KafkaProducerMessageHandler<String,Object>(kafkaTemplate());
        kafkaProducerMessageHandler.setMessageKeyExpression(new LiteralExpression("test"));
        kafkaProducerMessageHandler.setTopicExpression(new LiteralExpression("test"));
        return kafkaProducerMessageHandler;
    }

    @Bean
    public KafkaTemplate<String,Object> kafkaTemplate(){
        return new KafkaTemplate<String,Object>(kafkaProducerFactory());
    }

    @Bean
    public ProducerFactory<String,Object> kafkaProducerFactory(){
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public Map<String,Object> producerConfig(){
        Map<String, Object> producerConfig = new HashMap<>();
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
        producerConfig.put(ProducerConfig.LINGER_MS_CONFIG,1);
        return producerConfig;
    }


}
