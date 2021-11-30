package com.sns.commons.resolver

import com.sns.commons.annotation.IsLoginUser
import com.sns.commons.oauth.LoginUser
import com.sns.commons.utils.log
import org.springframework.core.MethodParameter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.oauth2.jwt.Jwt
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
class LoginUserResolver : HandlerMethodArgumentResolver {
    val log = this.log()

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val principal = SecurityContextHolder.getContext().authentication.principal
        if (principal is Jwt) {
            log.error("claims : {}", principal.claims)
            return LoginUser.from(principal.claims)
        }
        throw UsernameNotFoundException("로그인 인증에 실패했습니다")
    }

    override fun supportsParameter(parameter: MethodParameter): Boolean = parameter.hasMethodAnnotation(IsLoginUser::class.java)
}
