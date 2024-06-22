package com.kb.spring.rabbitmq.demo.config;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;


/**
 * This class is for the dynamic configuration of RabbitMQ queues. It creates Queues dynamically
 * which are mentioned in the application.properties file
 */
@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {

  private final Environment env;

  @Value("${rabbitmq.exchange}")
  private String exchangeName;

  @Value("${rabbitmq.queues}")
  private String[] queueNames;

  @Bean
  public DirectExchange exchange() {
    return new DirectExchange(exchangeName);
  }

  @Bean
  public Map<String, Queue> queues() {
    Map<String, Queue> queues = new HashMap<>();
    for (String queueName : queueNames) {
      queues.put(queueName, new Queue(queueName, true));
    }
    return queues;
  }

  @Bean
  public Map<String, Binding> bindings(DirectExchange exchange) {
    Map<String, Binding> bindings = new HashMap<>();
    for (String queueName : queueNames) {
      String routingKey = env.getProperty("rabbitmq.queue." + queueName + ".routing-key");
      bindings.put(queueName,
          BindingBuilder.bind(queues().get(queueName)).to(exchange).with(routingKey));
    }
    return bindings;
  }
}
