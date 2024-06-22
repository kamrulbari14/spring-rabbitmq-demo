package com.kb.spring.rabbitmq.demo.listener.factory;

import com.kb.spring.rabbitmq.demo.annotation.QueueStrategy;
import com.kb.spring.rabbitmq.demo.listener.strategy.MessageProcessingStrategy;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Factory class to manage and provide message processing strategies for different queues.
 */
@Component
@RequiredArgsConstructor
public class MessageProcessingStrategyFactory {

  private final ApplicationContext applicationContext;

  private final Map<String, MessageProcessingStrategy> strategies = new HashMap<>();

  /**
   * Initializes the factory by discovering and registering message processing strategies
   * dynamically.
   */
  @PostConstruct
  public void init() {
    Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(
        QueueStrategy.class);
    for (Object bean : beansWithAnnotation.values()) {
      QueueStrategy annotation = bean.getClass().getAnnotation(QueueStrategy.class);
      if (bean instanceof MessageProcessingStrategy) {
        strategies.put(annotation.value(), (MessageProcessingStrategy) bean);
      }
    }
  }

  /**
   * Gets the message processing strategy for the specified queue.
   *
   * @param queueName the name of the queue
   * @return the message processing strategy for the specified queue
   */
  public MessageProcessingStrategy getStrategy(String queueName) {
    return strategies.get(queueName);
  }
}
