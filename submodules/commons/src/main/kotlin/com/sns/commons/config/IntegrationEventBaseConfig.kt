package com.sns.commons.config

import com.sns.commons.service.EventPublisher
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean

class IntegrationEventBaseConfig {

    @Bean
    fun eventPublisher(applicationContext: ApplicationContext) = EventPublisher(applicationContext)
}
