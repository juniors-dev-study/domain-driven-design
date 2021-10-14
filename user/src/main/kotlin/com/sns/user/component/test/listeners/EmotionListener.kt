package com.sns.user.component.test.listeners

import com.sns.user.component.test.dtos.LaughingEvent
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
class EmotionListener {
    val log = LoggerFactory.getLogger(javaClass)

    @EventListener(LaughingEvent::class)
    @Order(1)
    fun onLaughing(event: LaughingEvent) {
        log.error("${event.who} is laughing")
    }

    @EventListener
    @Order(2)
    fun actionLaughing(event: LaughingEvent) {
        log.error("하 하 하")
    }
}
