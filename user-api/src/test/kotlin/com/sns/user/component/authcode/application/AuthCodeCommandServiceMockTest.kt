package com.sns.user.component.authcode.application

import com.sns.user.component.authcode.domain.AuthCode
import com.sns.user.component.authcode.domain.AuthCodeKey
import com.sns.user.component.authcode.domain.Purpose
import com.sns.user.component.authcode.repositories.AuthCodeRepository
import com.sns.user.component.user.domains.User
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

class AuthCodeCommandServiceMockTest() {
    @MockK
    private lateinit var authCodeRepository: AuthCodeRepository

    @MockK
    private lateinit var mailService: MailService

    @InjectMockKs
    private lateinit var authCodeCommandService: AuthCodeCommandService

    @BeforeEach
    internal fun setUp() {
        MockKAnnotations.init(this)
        every { mailService.sendSignUpAuthCodeMail(ofType(String::class), ofType(String::class)) } returns Unit
        every { authCodeRepository.save(any()) } returnsArgument 0
        every { authCodeRepository.delete(any()) } returns Unit
    }

    @Test
    fun create() {
        val authCode = authCodeCommandService.create(User.create("id", "passwd", "name", "email"))

        verify { authCodeRepository.save(eq(authCode)) }
        verify { mailService.sendSignUpAuthCodeMail(any(), any()) }
    }

    @DisplayName("userId, purpose에 맞는 authcode 기록이 없다면, 인증 실패해야한다.")
    @Test
    fun verify_null() {
        every { authCodeRepository.findByAuthCodeKey(ofType(AuthCodeKey::class)) } returns null

        authCodeCommandService.verify("userId", Purpose.SIGN_UP, "123") isEqualTo false
    }

    @DisplayName("정상 케이스인 경우, 인증에 성공해야한다.")
    @Test
    fun verify_success() {
        val authCode = AuthCode.createSignUp("userId")
        every { authCodeRepository.findByAuthCodeKey(ofType(AuthCodeKey::class)) } returns authCode

        authCodeCommandService.verify("userId", Purpose.SIGN_UP, authCode.code) isEqualTo true
    }

    @DisplayName("인증 코드가 다른 경우, 인증에 실패해야한다.")
    @Test
    fun verify_different_code() {
        val authCode = AuthCode.createSignUp("userId")
        every { authCodeRepository.findByAuthCodeKey(ofType(AuthCodeKey::class)) } returns authCode

        authCodeCommandService.verify("userId", Purpose.SIGN_UP, "different") isEqualTo false
    }
}
