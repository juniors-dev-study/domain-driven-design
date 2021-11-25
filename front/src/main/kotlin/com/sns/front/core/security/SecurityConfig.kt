package com.sns.front.core.security

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    val oauthUserService: OauthUserService
) : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity?) {
        http!!.authorizeRequests()
            .antMatchers("/home", "/auth-api/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .oauth2Client()
            .and()
            .logout()
            .logoutSuccessUrl("/home")
            .and()
            .oauth2Login() // 기본제공 로그인 사용.
            .defaultSuccessUrl("/home")
            .userInfoEndpoint()
            .userService(oauthUserService)
    }
}
