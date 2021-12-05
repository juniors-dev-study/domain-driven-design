package com.sns.front.core.security

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.logout.ServerLogoutHandler
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

/**
 * @author Hyounglin Jun
 */
@EnableWebFluxSecurity
class WebFluxSecurityConfig {
    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain? {
        http
            .authorizeExchange().pathMatchers("/", "/home", "/register", "/auth-api/**", "/css/**", "/js/**").permitAll().and() // 인증이 필요 없는 path
            .authorizeExchange().anyExchange().authenticated().and()    // 나머지 path는 모두 인증 필요
            .oauth2Client().and()
            .logout().logoutUrl("/logout").logoutHandler(CustomLogoutHandler()).and() // 로그아웃 로직 더 봐야함
            .oauth2Login()


        return http.build()
    }
}

@Component
class CustomLogoutHandler() : ServerLogoutHandler {
    override fun logout(exchange: WebFilterExchange?, authentication: Authentication?): Mono<Void> {
        TODO("Not yet implemented")
    }
}
