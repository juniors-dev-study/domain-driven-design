package com.sns.user.core.supports.securities.authentications

import com.sns.user.component.user.repositories.UserCrudRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class LoginUserService(
    private val userCrudRepository: UserCrudRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        if (username.isNullOrEmpty()) return InvalidUser()
        return LoginUser.create(userCrudRepository.findById(username).orElse(null))
    }
}
