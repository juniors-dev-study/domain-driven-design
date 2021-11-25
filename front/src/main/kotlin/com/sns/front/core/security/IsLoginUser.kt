package com.sns.front.core.security

import org.springframework.security.access.annotation.Secured

// Role.USER.role
@Secured("ROLE_USER")
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class IsLoginUser()
