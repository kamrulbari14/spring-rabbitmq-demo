package com.kb.spring.rabbitmq.demo.listener.strategy;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;

/**
 * Interface for message processing strategy. Each implementation of this interface will define how
 * to process messages from a specific queue.
 */
public interface MessageProcessingStrategy {

  /**
   * Processes a message from the queue.
   *
   * @param message the message to be processed
   * @param channel the channel to acknowledge the message
   * @throws Exception if any error occurs during message processing
   */
  void processMessage(Message message, Channel channel) throws Exception;
}
