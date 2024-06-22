package com.kb.spring.rabbitmq.demo.listener;

import com.kb.spring.rabbitmq.demo.listener.factory.MessageProcessingStrategyFactory;
import com.kb.spring.rabbitmq.demo.listener.strategy.MessageProcessingStrategy;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Dynamic RabbitMQ listener to process messages using different strategies based on the queue.
 */
@Component
@RequiredArgsConstructor
public class DynamicRabbitMQListener {

  private final MessageProcessingStrategyFactory strategyFactory;

  /**
   * Processes messages from any queue listed in the properties file.
   *
   * @param message the message to be processed
   * @param channel the channel to acknowledge the message
   * @throws Exception if any error occurs during message processing
   */
  @RabbitListener(queues = "${rabbitmq.queues}")
  public void onMessage(Message message, Channel channel) throws Exception {
    String queueName = message.getMessageProperties().getConsumerQueue();
    MessageProcessingStrategy strategy = strategyFactory.getStrategy(queueName);
    if (strategy != null) {
      strategy.processMessage(message, channel);
    } else {
      // Log error or handle the case where no strategy is found
      System.err.println("No strategy found for queue: " + queueName);
    }
  }
}
