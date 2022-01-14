package com.sns.article.core.config

/**
 * @author Hyounglin Jun
 */
import com.sns.commons.config.ResourceServerSecurityConfig
import com.sns.commons.config.WebConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@Import(ResourceServerSecurityConfig::class, WebConfig::class)
class SecurityConfig {
    @Bean
    fun passwordEncoder(): PasswordEncoder? = PasswordEncoderFactories.createDelegatingPasswordEncoder()
}

