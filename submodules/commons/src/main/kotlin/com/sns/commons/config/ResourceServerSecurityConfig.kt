package com.sns.commons.config

import com.sns.commons.oauth.Role
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import javax.crypto.spec.SecretKeySpec

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
class ResourceServerSecurityConfig : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.inMemoryAuthentication()
    }

    override fun configure(http: HttpSecurity?) {
        if (http == null) return
        http.cors().disable()
        http.authorizeRequests()
            .antMatchers("/**").permitAll()
            .antMatchers("/admin-api").hasRole(Role.ADMIN.name)
            .anyRequest().permitAll()
            .and().csrf().disable()
        http.oauth2ResourceServer()
            .jwt()
        http.headers().frameOptions().disable()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
    }

    override fun configure(web: WebSecurity?) {
        super.configure(web)
    }

    @Bean
    fun corsConfigurationSource(jwtDecoder: JwtDecoder): CorsConfigurationSource? {
        val configuration = CorsConfiguration()
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    @Profile("test")
    @Bean
    fun jwtDecoder(): JwtDecoder = NimbusJwtDecoder.withSecretKey(SecretKeySpec("key".toByteArray(), "RSA256")).build()
}
