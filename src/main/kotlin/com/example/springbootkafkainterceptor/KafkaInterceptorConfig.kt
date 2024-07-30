package com.example.springbootkafkainterceptor

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaConsumerFactoryCustomizer
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaProducerFactoryCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KafkaInterceptorConfig {
    @Bean
    @ConditionalOnProperty(name = ["spring.kafka.producer.user-holder-interceptor-enabled"], havingValue = "true")
    fun producerFactoryCustomizer(): DefaultKafkaProducerFactoryCustomizer {
        return DefaultKafkaProducerFactoryCustomizer { producerFactory ->
            val configProps = HashMap(producerFactory.configurationProperties)
            configProps[ProducerConfig.INTERCEPTOR_CLASSES_CONFIG] = UserHolderProducerInterceptor::class.java.name
            producerFactory.updateConfigs(configProps)
        }
    }

    @Bean
    @ConditionalOnProperty(name = ["spring.kafka.consumer.user-holder-interceptor-enabled"], havingValue = "true")
    fun consumerFactoryCustomizer(): DefaultKafkaConsumerFactoryCustomizer {
        return DefaultKafkaConsumerFactoryCustomizer { consumerFactory ->
            val configProps = HashMap(consumerFactory.configurationProperties)
            configProps[ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG] = UserHolderConsumerInterceptor::class.java.name
            consumerFactory.updateConfigs(configProps)
        }
    }
}
