package com.example.springbootkafkainterceptor

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import java.util.concurrent.CountDownLatch


@Component
class KafkaConsumer {
    private var latch = CountDownLatch(1)
    private var payload: String? = null

    private var user: User? = null

    @KafkaListener(topics = ["\${test.topic}"], containerFactory = "customKafkaListenerContainerFactory")
    fun receive(consumerRecord: ConsumerRecord<*, *>) {
        LOGGER.info("received payload='{}'", consumerRecord.toString())
        LOGGER.info("User='{}'", UserHolder.get())

        payload = consumerRecord.toString()
        user = UserHolder.get()

        latch.countDown()
    }

    fun getLatch(): CountDownLatch {
        return latch
    }

    fun getPayload(): String? {
        return payload
    }

    fun getUser(): User? {
        return user
    }

    fun resetLatch() {
        latch = CountDownLatch(1)
    } // other getters

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(KafkaConsumer::class.java)
    }
}
