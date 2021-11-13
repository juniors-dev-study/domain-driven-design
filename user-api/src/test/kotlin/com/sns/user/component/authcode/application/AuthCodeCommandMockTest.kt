package com.sns.user.component.authcode.application

import com.sns.user.component.authcode.domain.AuthCode
import com.sns.user.component.authcode.domain.Purpose
import com.sns.user.component.authcode.repositories.AuthCodeRepository
import com.sns.user.component.user.domains.User
import com.sns.user.component.user.repositories.DefaultUserRepository
import com.sns.user.core.infrastructures.mail.MailService
import com.sns.user.isEqualTo
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull

class AuthCodeCommandMockTest() {
    @MockK
    private lateinit var authCodeRepository: AuthCodeRepository

    @MockK
    private lateinit var mailService: MailService

    @MockK
    private lateinit var userRepository: DefaultUserRepository

    @InjectMockKs
    private lateinit var authCodeCommand: AuthCodeCommand

    @BeforeEach
    internal fun setUp() {
        MockKAnnotations.init(this)
        every { mailService.sendSignUpAuthCodeMail(ofType(String::class), ofType(String::class)) } returns Unit
        every { authCodeRepository.save(any()) } returnsArgument 0
        every { userRepository.findByIdOrNull(any()) } returns User.create("id", "pass", "name", "mail@gmail.com")
    }

    @Test
    fun create() {
        val authCode = authCodeCommand.create("id")

        verify { authCodeRepository.save(eq(authCode)) }
        verify { mailService.sendSignUpAuthCodeMail(any(), any()) }
    }

    @DisplayName("userId, purpose에 맞는 authcode 기록이 없다면, 인증 실패해야한다.")
    @Test
    fun verify_null() {
        every { authCodeRepository.findByUserIdAndPurpose(ofType(String::class), ofType(Purpose::class)) } returns null

        authCodeCommand.verify("userId", Purpose.SIGN_UP, "123") isEqualTo false
    }

    @DisplayName("정상 케이스인 경우, 인증에 성공해야한다.")
    @Test
    fun verify_success() {
        val authCode = AuthCode.createSignUp("userId")
        every { authCodeRepository.findByUserIdAndPurpose(ofType(String::class), ofType(Purpose::class)) } returns authCode

        authCodeCommand.verify("userId", Purpose.SIGN_UP, authCode.code) isEqualTo true
    }

    @DisplayName("인증 코드가 다른 경우, 인증에 실패해야한다.")
    @Test
    fun verify_different_code() {
        val authCode = AuthCode.createSignUp("userId")
        every { authCodeRepository.findByUserIdAndPurpose(ofType(String::class), ofType(Purpose::class)) } returns authCode

        authCodeCommand.verify("userId", Purpose.SIGN_UP, "different") isEqualTo false
    }
}
