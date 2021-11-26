package com.sns.commons.annotation

import org.springframework.security.access.prepost.PreAuthorize

// Role.USER.role
// @Secured("ROLE_USER")
@PreAuthorize("#oauth2.hasScope('read')")       // ROLE 로 변환하지않았음.
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class IsLoginUser()
