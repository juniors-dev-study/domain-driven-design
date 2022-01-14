package com.sns.article.core.config

/**
 * @author Hyounglin Jun
 */
import com.sns.commons.config.ResourceServerSecurityConfig
import com.sns.commons.config.WebConfig
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(ResourceServerSecurityConfig::class, WebConfig::class)
class SecurityConfig {
}

