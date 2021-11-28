package com.sns.front.core.security

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
class SecurityConfig(
    val oauthUserService: OauthUserService
) : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity?) {
        http!!.authorizeRequests()
            .antMatchers("/", "/home", "/register", "/auth-api/**", "/css/**", "/js/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .oauth2Client()
            .and()
            .logout()
            .logoutUrl("/logout")
            // authServer의 세션까지 없애야 로그아웃 후 자동 /oauth/authorize 인증이 막힘.
            .logoutSuccessUrl("http://local-auth.ddd.sns.com:10010/logout")
            .clearAuthentication(true)
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .addLogoutHandler(SecurityLogoutHandler())
            .permitAll()
            .and()
            .oauth2Login() // 기본제공 로그인 사용.
            .defaultSuccessUrl("/login?logout")
            .userInfoEndpoint()
            .userService(oauthUserService)
    }
}

@Component
class SecurityLogoutHandler(
) : LogoutHandler {
    val log = LoggerFactory.getLogger(this.javaClass)
    override fun logout(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {
        log.debug("logout succeed")
    }
}
