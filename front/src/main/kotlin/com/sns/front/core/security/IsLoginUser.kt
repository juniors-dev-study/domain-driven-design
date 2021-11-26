package com.sns.front.core.security

import org.springframework.security.access.annotation.Secured

/**
 * 토큰정보 캐싱필요.
 */
// Role.USER.role
@Secured("ROLE_USER")
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class IsLoginUser()
