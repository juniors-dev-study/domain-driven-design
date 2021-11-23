package com.sns.user.core.config

import com.sns.commons.DomainEvent
import com.sns.commons.config.IntegrationEventBaseConfig
import com.sns.commons.utils.log
import com.sns.user.component.test.dtos.LaughingEvent
import com.sns.user.component.test.listeners.EmotionListener
import com.sns.user.component.user.domains.Status
import com.sns.user.component.user.dtos.FriendRequestApprovedEvent
import com.sns.user.component.user.dtos.FriendRequestRejectedEvent
import com.sns.user.component.user.dtos.FriendRequestedEvent
import com.sns.user.component.user.events.UserStatusChangedEvent
import com.sns.user.component.user.listeners.FriendListener
import com.sns.user.component.user.listeners.UserStatusListener
import kotlin.reflect.KClass
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.integration.dsl.integrationFlow
import org.springframework.integration.handler.GenericHandler
import org.springframework.integration.handler.LambdaMessageProcessor
import org.springframework.integration.handler.ServiceActivatingHandler
import org.springframework.messaging.Message
import org.springframework.messaging.MessageHandler
import org.springframework.messaging.support.GenericMessage

@Import(IntegrationEventBaseConfig::class)
@Configuration
class IntegrationConfig {
    val log = this.log()

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
        handle<UserStatusChangedEvent> { event, _ ->
            when (event.user.status) {
                Status.CREATED -> userStatusListener.onCreated(event)
                Status.ACTIVATED -> userStatusListener.onActivated(event)
                Status.DELETED -> userStatusListener.onDelete(event)
            }
        }
    }

    @Bean
    fun friendFlow(friendListener: FriendListener) = integrationFlow {
        channel { publishSubscribe(Channels.FRIEND_REQUEST) }
        handle(
            handler(friendListener) {
                register<FriendRequestedEvent> { event, _ ->
                    friendListener.friendRequested(event)
                }
                register<FriendRequestApprovedEvent> { event, _ ->
                    friendListener.friendRequestApproved(event)
                }
                register<FriendRequestRejectedEvent> { event, _ ->
                    friendListener.friendRequestRejected(event)
                }
            },
        )
    }

    fun handler(
        listener: Any,
        init: GenericRouteServiceActivatingHandler.() -> Unit
    ): MessageHandler {
        val handler = GenericRouteServiceActivatingHandler(listener)
        handler.init()

        return handler
    }

    object Channels {
        const val EMOTION = "EMOTION_CHANNEL"
        const val USER_STATUS = "USER_STATUS_CHANNEL"
        const val FRIEND_REQUEST = "FRIEND_REQUEST_CHANNEL"
    }

    class GenericRouteServiceActivatingHandler(listener: Any) : ServiceActivatingHandler(listener) {
        val processorMap = mutableMapOf<KClass<*>, LambdaMessageProcessor>()

        inline fun <reified E : DomainEvent> register(genericHandler: GenericHandler<E>) {
            processorMap[E::class] = LambdaMessageProcessor(genericHandler, E::class.java)
        }

        override fun handleRequestMessage(message: Message<*>?): Any? {
            val genericMessage = message as GenericMessage<*>

            return processorMap.getValue(genericMessage.payload::class).processMessage(message)
        }
    }
}
