package com.sns.user.component.authcode.repositories

import com.sns.user.component.authcode.domain.AuthCode
import com.sns.user.component.authcode.domain.AuthCodeKey

interface AuthCodeRepository {
    fun save(authCode: AuthCode): AuthCode
    fun findByAuthCodeKey(authCodeKey: AuthCodeKey): AuthCode?
    fun delete(authCodeKey: AuthCodeKey)
}
