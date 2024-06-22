package com.kb.spring.rabbitmq.demo.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation to mark a class as a message processing strategy for a specific queue.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface QueueStrategy {

  /**
   * The name of the queue this strategy processes messages for.
   *
   * @return the queue name
   */
  String value();
}
