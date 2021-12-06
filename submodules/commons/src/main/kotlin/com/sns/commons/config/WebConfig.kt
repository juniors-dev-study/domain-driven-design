package com.sns.commons.config

import com.sns.commons.resolver.LoginUserResolver
import com.sns.commons.resolver.LoginUserResolvers
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Import(LoginUserResolvers::class)
@Configuration
class WebConfig(val loginUserResolver: LoginUserResolver) : WebMvcConfigurer {
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(loginUserResolver)
    }
}
