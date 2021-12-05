package com.sns.commons.config

import com.sns.commons.resolver.LoginUserResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer

@Configuration
class WebConfig() : WebFluxConfigurer {
    override fun configureArgumentResolvers(configurer: ArgumentResolverConfigurer) {
        configurer.addCustomResolver(loginUserResolver())
    }

    @Bean
    fun loginUserResolver() = LoginUserResolver()
}
