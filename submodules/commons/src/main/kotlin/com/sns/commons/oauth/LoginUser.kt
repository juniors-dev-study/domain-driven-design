package com.sns.commons.oauth

data class LoginUser(
    val name: String? = null,
    val scope: List<String>? = listOf(),
    val authorities: List<String>? = listOf(),
    val clientId: String? = null
) {
    companion object {
        fun from(map: Map<String, Any>): LoginUser = LoginUser(
            name = map.getOrDefault("user_name", "") as String,
            clientId = map.getOrDefault("client_id", "") as String,
            scope = map.getOrDefault("scope", listOf<String>()) as List<String>,
            authorities = map.getOrDefault("authorities", listOf<String>()) as List<String>,
        )
    }
}