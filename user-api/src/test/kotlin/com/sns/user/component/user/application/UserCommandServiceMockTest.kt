package com.sns.user.component.user.application

import com.sns.commons.DomainEvent
import com.sns.user.component.user.domains.User
import com.sns.user.component.user.events.UserActivatedEvent
import com.sns.user.component.user.events.UserCreatedEvent
import com.sns.user.component.user.repositories.UserRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder

internal class UserCommandServiceMockTest {
    @MockK
    private lateinit var userRepository: UserRepository

    @MockK
    private lateinit var passwordEncoder: PasswordEncoder

    @MockK
    private lateinit var eventPublisher: ApplicationEventPublisher

    @InjectMockKs
    lateinit var userCommandService: UserCommandService

    @BeforeEach
    internal fun setUp() {
        MockKAnnotations.init(this)

        val user = User.create("id", "passwd", "이름", "dev123@gmail.com")
        every { eventPublisher.publishEvent(ofType(DomainEvent::class)) } returns Unit
        every { userRepository.save(any()) } returnsArgument 0
        every { userRepository.findByInfoEmailAddressOrNull(ofType(String::class)) } returns user
        every { userRepository.findByIdOrNull(ofType(String::class)) } returns user
        every { passwordEncoder.encode(any()) } returnsArgument 0
    }

    @Test
    fun create() {
        userCommandService.create("이름", "passwd", "dev123@gmail.com")

        verify { eventPublisher.publishEvent(ofType(UserCreatedEvent::class)) }
        verify { userRepository.save(ofType(User::class)) }
    }

    @Test
    fun activate() {
        userCommandService.activate("dev123@gmail")

        verify { eventPublisher.publishEvent(ofType(UserActivatedEvent::class)) }
        verify { userRepository.save(ofType(User::class)) }
    }
}
