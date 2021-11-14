package com.sns.user.component.user.application

import com.sns.user.component.user.domains.User
import com.sns.user.component.user.repositories.UserRepository
import com.sns.user.core.exceptions.NoAuthorityException
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserCommandService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val eventPublisher: ApplicationEventPublisher
) {

    fun create(name: String, password: String, email: String): User {
        val user = User.create(email, passwordEncoder.encode(password), name, email) {
            eventPublisher.publishEvent(it)
        }
        userRepository.save(user)
        return user
    }

    fun activate(userId: String) {
        val user = userRepository.findByIdOrNull(userId) ?: throw NoAuthorityException()

        user.activate() {
            eventPublisher.publishEvent(it)
        }
        userRepository.save(user)
    }
}
