package com.sns.commons.service

import com.sns.commons.DomainEvent
import com.sns.commons.utils.log
import org.springframework.context.ApplicationContext
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.support.GenericMessage

class EventPublisher(private val applicationContext: ApplicationContext) {
    private val log = log()

    fun <T : DomainEvent> publish(domainEvent: T) {
        val channel = applicationContext.getBean(domainEvent.channel) as MessageChannel

        log.info("publish ${domainEvent.eventId}")

        channel.send(GenericMessage(domainEvent))
    }
}
