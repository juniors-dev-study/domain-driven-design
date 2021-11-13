package com.sns.user.component.authcode.application

import com.sns.user.component.authcode.domain.AuthCode
import com.sns.user.component.authcode.domain.Purpose
import com.sns.user.component.authcode.repositories.AuthCodeRepository
import com.sns.user.component.user.repositories.DefaultUserRepository
import com.sns.user.core.exceptions.NoAuthorityException
import com.sns.user.core.infrastructures.mail.MailService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class AuthCodeCommand(
    val authCodeRepository: AuthCodeRepository,
    val mailService: MailService,
    val userRepository: DefaultUserRepository
) {
    fun create(userId: String): AuthCode {
        val user = userRepository.findByIdOrNull(userId) ?: throw NoAuthorityException()

        val authCode = AuthCode.createSignUp(user.id)
        authCodeRepository.save(authCode)

        mailService.sendSignUpAuthCodeMail(authCode.code, user.infoEmailAddress)
        return authCode
    }

    fun verify(userId: String, purpose: Purpose, code: String): Boolean {
        val authCode: AuthCode? = authCodeRepository.findByUserIdAndPurpose(userId, purpose)
        return authCode?.isCorrect(userId, code, purpose)
            .takeIf { it == true }.apply {
                // TOOD update STATUS userRepository.save()
            } ?: false
    }
}
