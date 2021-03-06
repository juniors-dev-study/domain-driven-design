package com.sns.user.component.authcode.repositories

import com.sns.user.component.authcode.domain.AuthCode
import com.sns.user.component.authcode.domain.AuthCodeKey
import com.sns.user.component.authcode.domain.Purpose
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AuthCodeRepositoryTest {
    @Autowired
    lateinit var authCodeRepository: AuthCodeRepository

    @Test
    internal fun save() {
        authCodeRepository.save(AuthCode.createSignUp("userId"))
    }

    @Test
    internal fun findByUserIdAndPurpose() {
        assertThat(authCodeRepository.findByAuthCodeKey(AuthCodeKey(Purpose.SIGN_UP, "userId"))).isNotNull
    }

    @Test
    internal fun delete() {
        authCodeRepository.delete(AuthCodeKey(Purpose.SIGN_UP, "userId"))
    }
}
