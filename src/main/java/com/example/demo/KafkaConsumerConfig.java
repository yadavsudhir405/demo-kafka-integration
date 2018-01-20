package com.example.demo;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.kafka.inbound.KafkaMessageDrivenChannelAdapter;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by sudhiry on 1/20/18.
 */
//@Configuration
public class KafkaConsumerConfig {

    private String bootstrapServers = "localhost:9092";
    private static final String KAFKA_TOPIC = "test";

    @Bean
    public DirectChannel fromKafka(){
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "fromKafka")
    public CountDownLatchHandler countDownLatchHandler(){
        return new CountDownLatchHandler();
    }

    @Bean
    public KafkaMessageDrivenChannelAdapter<String, String> kafkMessageDrivenChannelAdapter
                    (ConcurrentMessageListenerContainer kafkaMessageListenerContainer, MessageChannel fromKafka){

        KafkaMessageDrivenChannelAdapter<String,String> kafkaMessageDrivenChannelAdapter =  new
                KafkaMessageDrivenChannelAdapter<String, String>(kafkaMessageListenerContainer);
        kafkaMessageDrivenChannelAdapter.setOutputChannel(fromKafka);
        return kafkaMessageDrivenChannelAdapter;
    }

    @Bean
    public ConcurrentMessageListenerContainer kafkaMessageListenerContainer(ConsumerFactory kafkaConsumerFactory){
        ContainerProperties containerProperties =
                new ContainerProperties(KAFKA_TOPIC);
        return new ConcurrentMessageListenerContainer(kafkaConsumerFactory, containerProperties);

    }

    @Bean
    public ConsumerFactory<?,?> kafkaConsumerFactory(Map<String,Object>  consumerConfig){
        return new DefaultKafkaConsumerFactory<Object, Object>(consumerConfig);
    }

    @Bean
    public Map<String,Object> consumerConfig(){
        Map<String,Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,String.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,String.class);
        config.put(ConsumerConfig.GROUP_ID_CONFIG,"test-consumer");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        return config;
    }



}

