package com.sns.user.core.supports.securities.authentications

import org.springframework.security.access.annotation.Secured

// Role.USER.role
@Secured("ROLE_USER")
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class IsLoginUser()
