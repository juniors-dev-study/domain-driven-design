package com.sns.authentication

import com.sns.authentication.user.UserCrudRepository
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class LoginUserService(
    private val userCrudRepository: UserCrudRepository
) : UserDetailsService {
    /**
     * @param username spring oauth2에서 의미하는 userName = user.id (unique key)
     */
    override fun loadUserByUsername(username: String?): LoginUser {
        if (username.isNullOrEmpty()) throw UsernameNotFoundException("유저 정보를 찾을 수 없습니다")
        return LoginUser.create(userCrudRepository.findById(username).orElse(null))
    }
}
