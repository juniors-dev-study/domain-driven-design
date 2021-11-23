package com.sns.authentication

import org.springframework.security.core.authority.SimpleGrantedAuthority

enum class Role(val role: String) {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    fun createSimpleGrantedAuthority(): SimpleGrantedAuthority = SimpleGrantedAuthority(this.role)
}
