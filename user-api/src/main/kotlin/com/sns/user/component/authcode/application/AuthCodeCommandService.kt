package com.sns.user.component.authcode.application

import com.sns.commons.utils.ifTrue
import com.sns.user.component.authcode.domain.AuthCode
import com.sns.user.component.authcode.domain.AuthCodeKey
import com.sns.user.component.authcode.domain.Purpose
import com.sns.user.component.authcode.repositories.AuthCodeRepository
import com.sns.user.component.user.domains.User
import com.sns.user.core.infrastructures.mail.MailService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthCodeCommandService(
    val authCodeRepository: AuthCodeRepository,
    val mailService: MailService,
) {

    @Transactional
    fun create(user: User): AuthCode {
        val authCode = AuthCode.createSignUp(user.id)
        authCodeRepository.save(authCode)

        mailService.sendSignUpAuthCodeMail(authCode.code, user.infoEmailAddress)
        return authCode
    }

    @Transactional
    fun verify(userId: String, purpose: Purpose, code: String): Boolean {
        val authCodeKey = AuthCodeKey(purpose, userId)
        val authCode: AuthCode? = authCodeRepository.findByAuthCodeKey(authCodeKey)
        return authCode?.isCorrect(userId, code, purpose)
            .ifTrue { authCodeRepository.delete(authCodeKey) } ?: false
    }
}
