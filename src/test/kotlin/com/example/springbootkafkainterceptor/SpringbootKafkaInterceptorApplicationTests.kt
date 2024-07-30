package com.example.springbootkafkainterceptor

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.annotation.DirtiesContext
import java.util.concurrent.TimeUnit


//@Import(TestcontainersConfiguration::class)
@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = ["listeners=PLAINTEXT://localhost:9092", "port=9092"])
class SpringbootKafkaInterceptorApplicationTests {

    @Autowired
    private val consumer: KafkaConsumer? = null

    @Autowired
    private val producer: KafkaProducer? = null

    @Value("\${test.topic}")
    private val topic: String? = null

    @Test
    @Throws(Exception::class)
    fun test() {
        val data = "Sending with our own simple KafkaProducer"

        producer!!.send(topic!!, data)

        val messageConsumed: Boolean = consumer!!.getLatch().await(10, TimeUnit.SECONDS)
        assertTrue(messageConsumed)
        assertTrue(consumer.getPayload()!!.contains(data))
        assertEquals(producer.getUser(), consumer.getUser())
    }
}
