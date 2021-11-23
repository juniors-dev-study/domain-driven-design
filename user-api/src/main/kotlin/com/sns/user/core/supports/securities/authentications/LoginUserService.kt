package com.sns.user.core.supports.securities.authentications

import com.sns.user.component.user.repositories.UserCrudRepository
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class LoginUserService(
    private val userCrudRepository: UserCrudRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String?): LoginUser {
        if (username.isNullOrEmpty()) throw UsernameNotFoundException("유저 정보를 찾을 수 없습니다")
        return LoginUser.create(userCrudRepository.findById(username).orElse(null))
    }
}
