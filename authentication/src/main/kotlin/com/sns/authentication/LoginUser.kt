package com.sns.authentication

import com.sns.authentication.config.Role
import com.sns.authentication.user.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException

class LoginUser(
    val id: String,
    val name: String,
    private val password: String
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf<GrantedAuthority>(Role.USER.createSimpleGrantedAuthority())
    override fun getUsername(): String = id
    override fun getPassword(): String = password
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true

    companion object {
        fun create(user: User?): LoginUser =
            if (user == null) throw UsernameNotFoundException("유저 정보를 찾을 수 없습니다.") else LoginUser(user.id, user.name, user.password)
    }
}
