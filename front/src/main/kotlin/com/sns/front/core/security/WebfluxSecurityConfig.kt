package com.sns.front.core.security

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.sns.front.utils.JsonUtil
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.ReactiveOAuth2UserService
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler
import org.springframework.stereotype.Service
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect
import reactor.core.publisher.Mono
import java.net.URI
import java.util.*

/**
 * @author Hyounglin Jun
 */
@EnableWebFluxSecurity
class WebfluxSecurityConfig {

    @Bean
    fun securityDialect(): SpringSecurityDialect {
        return SpringSecurityDialect()
    }

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain? {
        val redirectServerLogoutSuccessHandler = getRedirectServerLogoutSuccessHandler()

        return http
            .authorizeExchange {
                it
                    .pathMatchers("/", "/home", "/auth-api/**", "/register", "/js/**", "/css/**", "/user-api/**", "/article-api/**").permitAll()
                    .anyExchange().authenticated()
            }
            .httpBasic().and()
            .oauth2Login(withDefaults())
            .logout().logoutSuccessHandler(redirectServerLogoutSuccessHandler).and()
            .csrf().disable()
            .build()
    }

    private fun getRedirectServerLogoutSuccessHandler(): RedirectServerLogoutSuccessHandler {
        return RedirectServerLogoutSuccessHandler().apply {
            setLogoutSuccessUrl(URI.create("http://local-auth.ddd.sns.com:10010/logout"))
        }
    }
}

@Service
class ReactiveOAuthUserService() : ReactiveOAuth2UserService<OAuth2UserRequest, OAuth2User> {
    val log = LoggerFactory.getLogger(this.javaClass)!!

    override fun loadUser(userRequest: OAuth2UserRequest): Mono<OAuth2User> {
        val clientId = userRequest.clientRegistration.clientId
        val authorities = mutableSetOf<GrantedAuthority>()
        val token = userRequest.accessToken
        val payload = JWTPayload.interpret(token.tokenValue)
        for (authority in token.scopes) {
            authorities += SimpleGrantedAuthority("SCOPE_$authority")
        }
        for (authority in payload.authorities) {
            authorities += SimpleGrantedAuthority(authority)
        }

        log.info("authUser : {}", payload.userName)
        return Mono.just(
            DefaultOAuth2User(
                authorities,
                mutableMapOf<String, Any>(
                    "clientId" to clientId,
                    "id" to payload.userName,
                ),
                "id",
            ),
        )
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class JWTPayload(
    @JsonProperty("user_name")
    var userName: String,
    @JsonProperty("authorities")
    var authorities: List<String>,
) {

    companion object {
        fun interpret(token: String): JWTPayload {
            // JWTDecoder?? 사용 검토 필요
            val chunks = token.split(".")
            return JsonUtil.read(String(Base64.getDecoder().decode(chunks[1])), JWTPayload::class.java)
        }
    }
}


