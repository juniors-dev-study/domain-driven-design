package com.sns.user.component.user.application

import com.sns.commons.DomainEvent
import com.sns.commons.exceptions.AlreadyExistException
import com.sns.commons.service.EventPublisher
import com.sns.user.component.user.domains.User
import com.sns.user.component.user.events.UserStatusChangedEvent
import com.sns.user.component.user.repositories.UserRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import java.util.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder

internal class UserCommandServiceMockTest {
    @MockK
    private lateinit var userRepository: UserRepository

    @MockK
    private lateinit var passwordEncoder: PasswordEncoder

    @MockK
    private lateinit var eventPublisher: EventPublisher

    @InjectMockKs
    lateinit var userCommandService: UserCommandService

    @BeforeEach
    internal fun setUp() {
        MockKAnnotations.init(this, relaxed = true)

        val user = User.create("id", "passwd", "이름", "dev123@gmail.com")
        every { eventPublisher.publish(ofType(DomainEvent::class)) } returns Unit
        every { userRepository.save(any()) } returnsArgument 0
        every { userRepository.findByInfoEmailAddress(ofType(String::class)) } returns Optional.of(user)
        every { userRepository.findByIdOrNull(ofType(String::class)) } returns user
        every { passwordEncoder.encode(any()) } returnsArgument 0
    }

    @Test
    fun create() {
        userCommandService.create("이름", "passwd", "dev123@gmail.com")

        verify { eventPublisher.publish(ofType(UserStatusChangedEvent::class)) }
        verify { userRepository.save(ofType(User::class)) }
    }

    @Test
    fun activate() {
        userCommandService.activate("dev123@gmail")

        verify { eventPublisher.publish(ofType(UserStatusChangedEvent::class)) }
        verify { userRepository.save(ofType(User::class)) }
    }

    @DisplayName("이미 ACTIVATED 상태라면, exception이 던져져야 한다.")
    @Test
    fun activate_already() {
        every { userRepository.findByIdOrNull(ofType(String::class)) } returns User("id", "passwd", "name")

        assertThrows<AlreadyExistException> { userCommandService.activate("dev123@gmail") }

        verify(exactly = 0) { eventPublisher.publish(ofType(UserStatusChangedEvent::class)) }
        verify(exactly = 0) { userRepository.save(ofType(User::class)) }
    }
}
