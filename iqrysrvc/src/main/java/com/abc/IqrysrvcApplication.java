package com.abc;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.abc.common.Constants;
import com.abc.service.ReceiverService;

@SpringBootApplication
@EnableDiscoveryClient
public class IqrysrvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(IqrysrvcApplication.class, args);
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
	
	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(Constants.QUEUE_NAME);
		container.setMessageListener(listenerAdapter);
		return container;
	}
/*
    @Bean
    ReceiverService receiver() {
        return new ReceiverService();
    }*/

	@Bean
	MessageListenerAdapter listenerAdapter(ReceiverService receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}
}
