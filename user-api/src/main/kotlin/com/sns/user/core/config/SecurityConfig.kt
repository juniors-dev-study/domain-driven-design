package com.sns.user.core.config

import com.sns.user.core.supports.securities.authentications.LoginUserService
import com.sns.user.core.supports.securities.authentications.Role
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@EnableWebSecurity
class SecurityConfig(
    private val loginUserService: LoginUserService
) : WebSecurityConfigurerAdapter() {

    val WHITE_LIST = arrayOf(
        "/",
        "/swagger-resources/**",
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/webjars/**",
        "/api/*/sign-up/**",
    )

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.userDetailsService(loginUserService)
            ?.passwordEncoder(passwordEncoder())
    }

    override fun configure(http: HttpSecurity?) {
        if (http == null) return
        http.authorizeRequests()
            .antMatchers(*WHITE_LIST).permitAll()
            .antMatchers("/admin-api").hasRole(Role.ADMIN.name)
            .anyRequest().authenticated()
            .and().logout().logoutSuccessUrl("/").invalidateHttpSession(true).permitAll()
            .and().formLogin().loginPage("/sign-in")
            .and().csrf().disable()
    }

    override fun configure(web: WebSecurity?) {
        super.configure(web)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }
}
