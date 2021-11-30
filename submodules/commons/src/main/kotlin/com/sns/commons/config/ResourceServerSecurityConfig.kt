package com.sns.commons.config

import com.sns.commons.oauth.Role
import org.springframework.context.annotation.Bean
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
class ResourceServerSecurityConfig : WebSecurityConfigurerAdapter() {

    val WHITE_LIST = arrayOf(
        "/",
        "/swagger-resources/**",
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/webjars/**",
        "/api/*/sign-up/**",
    )

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.inMemoryAuthentication()
    }

    override fun configure(http: HttpSecurity?) {
        if (http == null) return
        http.cors(Customizer.withDefaults())
        http.authorizeRequests()
            .antMatchers("/**").permitAll()
            .antMatchers("/admin-api").hasRole(Role.ADMIN.name)
            .anyRequest().permitAll()
            .and().csrf().disable()
        http.oauth2ResourceServer()
            .jwt()
        http.headers().frameOptions().disable()
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
}
