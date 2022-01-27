package com.sns.user.component.user.application

import com.sns.commons.exceptions.AlreadyExistException
import com.sns.commons.exceptions.NoAuthorityException
import com.sns.commons.service.EventPublisher
import com.sns.user.component.user.domains.User
import com.sns.user.component.user.repositories.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserCommandService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val eventPublisher: EventPublisher
) {

    @Transactional
    fun create(name: String, password: String, email: String): User {
        userRepository.findById(email).ifPresent { throw AlreadyExistException() }

        val user = User.create(email, passwordEncoder.encode(password), name, email) {
            eventPublisher.publish(it)
        }
        userRepository.save(user)
        return user
    }

    @Transactional
    fun activate(userId: String) {
        val user = userRepository.findByIdOrNull(userId) ?: throw NoAuthorityException()

        user.activate() {
            eventPublisher.publish(it)
        }
        userRepository.save(user)
    }

    fun delete(userId: String) {
        val user = userRepository.findByIdOrNull(userId) ?: throw NoAuthorityException()
        user.delete {
            eventPublisher.publish(it)
        }
    }
}
