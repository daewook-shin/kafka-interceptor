package com.example.springbootkafkainterceptor

import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.listener.RecordInterceptor
import java.nio.charset.StandardCharsets

class UserHolderRecordInterceptor : RecordInterceptor<String, String> {
    override fun intercept(
        record: ConsumerRecord<String, String>,
        consumer: Consumer<String, String>,
    ): ConsumerRecord<String, String> {
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
        return record
    }
}
