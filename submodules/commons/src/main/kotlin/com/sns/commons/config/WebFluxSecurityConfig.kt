package com.sns.commons.config

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsConfigurationSource
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource

/**
 * @author Hyounglin Jun
 */
@EnableWebFluxSecurity
class WebFluxSecurityConfig {
    val WHITE_LIST = arrayOf(
        "/",
        "/swagger-resources/**",
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/webjars/**",
        "/api/*/sign-up/**",
    )

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain? {
        http
            .authorizeExchange().pathMatchers(*WHITE_LIST).permitAll().and() // 인증이 필요 없는 path
            .authorizeExchange().anyExchange().authenticated().and()    // 나머지 path는 모두 인증 필요
            .logout().logoutUrl("/logout").and() // 로그아웃 로직 더 봐야함
            .oauth2ResourceServer().jwt()   // resource server이고 jwt 사용

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource? {
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", CorsConfiguration())
        return source
    }
}
