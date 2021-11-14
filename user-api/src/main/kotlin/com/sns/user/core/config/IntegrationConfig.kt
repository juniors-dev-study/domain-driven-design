package com.sns.user.core.config

import com.sns.commons.config.IntegrationEventBaseConfig
import com.sns.user.component.test.dtos.LaughingEvent
import com.sns.user.component.test.listeners.EmotionListener
import com.sns.user.component.user.events.UserActivatedEvent
import com.sns.user.component.user.events.UserCreatedEvent
import com.sns.user.component.user.listeners.UserStatusListener
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

    @Bean
    fun userStatusFlow(userStatusListener: UserStatusListener) = integrationFlow {
        channel { publishSubscribe(Channels.USER_STATUS) }
        handle<UserCreatedEvent> { event, _ ->
            userStatusListener.onCreated(event)
        }
        handle<UserActivatedEvent> { event, _ ->
            userStatusListener.onActivated(event)
        }
    }

    object Channels {
        const val EMOTION = "EMOTION_CHANNEL"
        const val USER_STATUS = "USER_STATUS_CHANNEL"
    }
}
