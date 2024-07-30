package com.example.springbootkafkainterceptor

enum class Role {
    STUDENT, TEACHER, ADMIN
}

data class User(val id: String, val role: Role)

object UserHolder {
    private val threadLocalUser: ThreadLocal<User?> = ThreadLocal()

    fun set(user: User) {
        threadLocalUser.set(user)
    }

    fun get(): User? {
        return threadLocalUser.get()
    }

    fun clear() {
        threadLocalUser.remove()
    }
}
