package com.sns.user.component.user.application

import com.sns.user.component.user.domains.User
import com.sns.user.component.user.repositories.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserQueryService(
    private val userRepository: UserRepository
) {
    fun getById(id: String): User? = userRepository.findByIdOrNull(id)

    fun getByEmail(email: String): User? = userRepository.findByInfoEmailAddress(email).orElse(null)
}
