package com.sns.user

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing

@SpringBootApplication
@EnableJdbcAuditing
class UserApplication

fun main(args: Array<String>) {
    runApplication<UserApplication>(*args)
}
