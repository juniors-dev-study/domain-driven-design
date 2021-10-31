package com.sns.localevent.config

import com.sns.localevent.service.EventPublisher
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean

class IntegrationEventBaseConfig {

    @Bean
    fun eventPublisher(applicationContext: ApplicationContext) = EventPublisher(applicationContext)
}
