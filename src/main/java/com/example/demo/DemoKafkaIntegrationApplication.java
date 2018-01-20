package com.example.demo;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.GenericMessage;

import java.util.Collections;
import java.util.Map;

@SpringBootApplication
public class DemoKafkaIntegrationApplication {


	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(DemoKafkaIntegrationApplication.class, args);
		DirectChannel toKafka = ctx.getBean("toKafka", DirectChannel.class);
		Map<String,Object> header = Collections.singletonMap(KafkaHeaders.TOPIC,"test");
		System.out.println("*************Sending messages**************");
		for(int  i=0;i<10;i++){
			toKafka.send(new GenericMessage<Object>("Hello Kafka  "+(i+1), header));
		}

	}


}
