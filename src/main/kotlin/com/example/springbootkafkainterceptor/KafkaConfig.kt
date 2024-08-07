package com.example.springbootkafkainterceptor

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory


@Configuration
class KafkaConfig {
    @Bean
    @ConditionalOnBean(name = ["customKafkaListenerContainerFactory"])
    fun customKafkaListenerContainerFactory(
        consumerFactory: ConsumerFactory<String, String>,
    ): ConcurrentKafkaListenerContainerFactory<String, String> {
        val factory =
            ConcurrentKafkaListenerContainerFactory<String, String>()
        factory.consumerFactory = consumerFactory
        factory.setRecordInterceptor(UserHolderRecordInterceptor())
        return factory
    }
}
