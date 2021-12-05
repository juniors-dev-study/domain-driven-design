package com.sns.commons.resolver

import com.sns.commons.annotation.IsLoginUser
import com.sns.commons.oauth.LoginUser
import com.sns.commons.utils.log
import org.springframework.core.MethodParameter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.reactive.BindingContext
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

/**
 * 로그인 유저 체크 완료 후, loginUser 주입
 * <pre>
 *     @IsLoginUser
 *     fun controllerMethod(loginUser : LoginUser)
 * <pre>
 */
class LoginUserResolver : HandlerMethodArgumentResolver {
    val log = this.log()

    override fun supportsParameter(parameter: MethodParameter): Boolean = parameter.hasMethodAnnotation(IsLoginUser::class.java)
    override fun resolveArgument(parameter: MethodParameter, bindingContext: BindingContext, exchange: ServerWebExchange): Mono<Any> {
        val principal = SecurityContextHolder.getContext().authentication.principal
        if (principal is Jwt) {
            log.error("claims : {}", principal.claims)
            return Mono.just(LoginUser.from(principal.claims))
        }
        throw UsernameNotFoundException("로그인 인증에 실패했습니다")
    }
}
