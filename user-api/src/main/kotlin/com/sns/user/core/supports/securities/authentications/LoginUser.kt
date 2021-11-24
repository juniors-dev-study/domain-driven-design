package com.sns.user.core.supports.securities.authentications

import com.sns.user.component.user.domains.User
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class LoginUser(val user: User) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf<GrantedAuthority>(Role.USER.createSimpleGrantedAuthority())
    override fun getUsername(): String = user.name
    override fun getPassword(): String = user.password
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true

    companion object {
        fun create(user: User?): UserDetails =
            if (user == null) InvalidUser() else LoginUser(user)
    }
}

class InvalidUser : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf()
    override fun getUsername(): String = ""
    override fun getPassword(): String = ""
    override fun isAccountNonExpired(): Boolean = false
    override fun isAccountNonLocked(): Boolean = false
    override fun isCredentialsNonExpired(): Boolean = false
    override fun isEnabled(): Boolean = false
}

fun Authentication.loginUser(): LoginUser = this.principal as LoginUser
