package com.sns.commons.oauth

import org.springframework.security.core.userdetails.User

data class LoginUser(
    val id: String? = null,
    val name: String? = null,
    val scope: List<String>? = listOf(),
    val authorities: List<String>? = listOf(),
    val clientId: String? = null
) {
    companion object {
        fun from(map: Map<String, Any>): LoginUser = LoginUser(
            id = map.getOrDefault("user_id", "") as String,
            name = map.getOrDefault("user_nickname", "") as String,
            clientId = map.getOrDefault("client_id", "") as String,
            scope = map.getOrDefault("scope", listOf<String>()) as List<String>,
            authorities = map.getOrDefault("authorities", listOf<String>()) as List<String>,
        )

        fun forTest(user: User) = LoginUser(
            id = user.username,
            name = user.username,
            authorities = user.authorities.map { toString() },
            scope = user.authorities.map { toString() },
        )
    }
}
