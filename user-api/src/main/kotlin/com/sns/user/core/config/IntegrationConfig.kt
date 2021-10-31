package com.sns.user.core.config

import com.sns.localevent.config.IntegrationEventBaseConfig
import com.sns.user.component.test.dtos.LaughingEvent
import com.sns.user.component.test.listeners.EmotionListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.integration.dsl.integrationFlow

@Import(IntegrationEventBaseConfig::class)
@Configuration
class IntegrationConfig {

    @Bean
    fun emotionFlow(emotionListener: EmotionListener) = integrationFlow {
        channel { publishSubscribe(Channels.EMOTION) }
        handle<LaughingEvent> { event, _ ->
            emotionListener.onLaughing(event)
        }
    }

    object Channels {
        const val EMOTION = "EMOTION_CHANNEL"
    }
}
