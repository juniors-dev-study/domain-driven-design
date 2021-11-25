package com.sns.front.utils

import com.fasterxml.jackson.databind.ObjectMapper

object JsonUtil {
    private val objectMapper: ObjectMapper = ObjectMapper()

    fun <T> read(string: String, clazz: Class<T>): T = objectMapper.readValue<T>(string, clazz)
}
