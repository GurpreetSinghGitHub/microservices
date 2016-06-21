package com.abc;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.abc.common.Constants;

@SpringBootApplication
@EnableDiscoveryClient
public class ImntsrvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImntsrvcApplication.class, args);
	}
	
	@Bean
	Queue queue() {
		return new Queue(Constants.QUEUE_NAME, false);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(Constants.EXCHANGE_NAME);
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(Constants.QUEUE_NAME);
	}
}
