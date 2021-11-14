package com.sns.user.component.authcode.repositories

import com.sns.user.component.authcode.domain.AuthCode
import com.sns.user.component.authcode.domain.Purpose
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface AuthCodeRepository {
    fun save(authCode: AuthCode): AuthCode
    fun findByUserIdAndPurpose(userId: String, purpose: Purpose): AuthCode?
}
