package com.example.springbootkafkainterceptor

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>,
) {
    private var user: User? = null

    fun send(topic: String, payload: String) {
        LOGGER.info("sending payload='{}' to topic='{}'", payload, topic)

        UserHolder.set(User("user1", Role.ADMIN))
        user = UserHolder.get()

        kafkaTemplate.send(topic, payload)
    }

    fun getUser(): User? {
        return user
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(KafkaProducer::class.java)
    }
}
