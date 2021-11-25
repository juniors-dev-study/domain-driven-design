package com.sns.user.core.config

import com.sns.commons.oauth.OauthResourceServerConfig
import com.sns.user.core.supports.securities.authentications.LoginUser
import com.sns.user.core.supports.securities.authentications.LoginUserService
import com.sns.user.core.supports.securities.authentications.Role
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Import(OauthResourceServerConfig::class)
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
        auth?.inMemoryAuthentication()
            ?.withUser(LoginUser("admin_user", "ADMIN_USER", passwordEncoder()?.encode("admin") ?: "admin"))
        auth?.userDetailsService(loginUserService)
            ?.passwordEncoder(passwordEncoder())
    }

    override fun configure(http: HttpSecurity?) {
        if (http == null) return
        http.cors(Customizer.withDefaults())
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
    fun passwordEncoder(): PasswordEncoder? = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource? {
        val configuration = CorsConfiguration()
        // configuration.allowedOrigins = Arrays.asList("https://example.com")
        // configuration.allowedMethods = Arrays.asList("GET", "POST")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}
