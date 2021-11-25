package com.sns.commons.oauth

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
class OauthResourceServerConfig : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http.oauth2ResourceServer()
            .jwt()
        http.headers().frameOptions().disable()
        http.authorizeRequests()
            .antMatchers("/").access("#oauth2.hasScope('read')")
            .anyRequest().authenticated()
    }
}
