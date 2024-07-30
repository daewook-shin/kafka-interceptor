package com.example.springbootkafkainterceptor

import org.apache.kafka.clients.consumer.ConsumerInterceptor
import org.apache.kafka.clients.consumer.ConsumerRecords
import java.nio.charset.StandardCharsets

class UserHolderConsumerInterceptor : ConsumerInterceptor<String, String> {

    override fun onConsume(records: ConsumerRecords<String, String>): ConsumerRecords<String, String> {
        for (record in records) {
            val header = record.headers().lastHeader("modified-by")
            if (header != null) {
                val headerValue = String(header.value(), StandardCharsets.UTF_8)
                val parts = headerValue.split(":")
                if (parts.size == 2) {
                    val role = Role.valueOf(parts[0])
                    val id = parts[1]
                    val user = User(id, role)

                    // set the user in the thread local
                    UserHolder.set(user)
                }
            }
        }
        return records
    }

    override fun onCommit(offsets: Map<org.apache.kafka.common.TopicPartition, org.apache.kafka.clients.consumer.OffsetAndMetadata>) {
    }

    override fun close() {
    }

    override fun configure(configs: Map<String, *>?) {
    }
}
