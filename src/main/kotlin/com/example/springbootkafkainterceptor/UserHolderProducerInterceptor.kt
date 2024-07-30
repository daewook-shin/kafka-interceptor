package com.example.springbootkafkainterceptor

import org.apache.kafka.clients.producer.ProducerInterceptor
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.apache.kafka.common.header.internals.RecordHeader
import java.nio.charset.StandardCharsets

class UserHolderProducerInterceptor : ProducerInterceptor<String, String> {
    override fun onSend(record: ProducerRecord<String, String>): ProducerRecord<String, String> {
        val user = UserHolder.get()
        if (user != null) {
            val modifiedBy = "${user.role}:${user.id}"
            println("modifiedBy: $modifiedBy")
            record.headers().add(RecordHeader("modified-by", modifiedBy.toByteArray(StandardCharsets.UTF_8)))
        }
        return record
    }

    override fun onAcknowledgement(metadata: RecordMetadata?, exception: Exception?) {
    }

    override fun close() {
    }

    override fun configure(configs: Map<String, *>?) {
    }
}
