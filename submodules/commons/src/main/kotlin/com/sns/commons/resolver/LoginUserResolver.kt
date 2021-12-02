package com.sns.commons.resolver

import com.sns.commons.annotation.IsLoginUser
import com.sns.commons.oauth.LoginUser
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.core.MethodParameter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

/**
 * 로그인 유저 체크 완료 후, loginUser 주입
 * <pre>
 *     @IsLoginUser
 *     fun controllerMethod(loginUser : LoginUser)
 * <pre>
 */
interface LoginUserResolver : HandlerMethodArgumentResolver

/**
 * 한번에 import 하기 위함.
 */
@Component
class LoginUserResolvers {
    @Profile("test")
    @Bean
    fun testUerResolver() = TestLoginUserResolver()

    @ConditionalOnMissingBean(LoginUserResolver::class)
    @Bean
    fun defaultUserResolver() = DefaultLoginUserResolver()
}

/**
 * withMockUser 사용을 위한 테스트용 체크
 */
class TestLoginUserResolver : LoginUserResolver {
    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): LoginUser {
        val principal = SecurityContextHolder.getContext().authentication.principal
        if (principal is User) {
            return LoginUser.forTest(principal)
        }
        throw UsernameNotFoundException("로그인 인증에 실패했습니다")
    }

    override fun supportsParameter(parameter: MethodParameter): Boolean = parameter.hasMethodAnnotation(IsLoginUser::class.java)
}

class DefaultLoginUserResolver : LoginUserResolver {
    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): LoginUser {
        val principal = SecurityContextHolder.getContext().authentication.principal
        if (principal is Jwt) {
            return LoginUser.from(principal.claims)
        }
        throw UsernameNotFoundException("로그인 인증에 실패했습니다")
    }

    override fun supportsParameter(parameter: MethodParameter): Boolean = parameter.hasMethodAnnotation(IsLoginUser::class.java)
}
