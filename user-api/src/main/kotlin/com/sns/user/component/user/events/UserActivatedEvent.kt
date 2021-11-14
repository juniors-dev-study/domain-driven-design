package com.sns.user.component.user.events

import com.sns.commons.DomainEvent
import com.sns.user.component.user.domains.User
import com.sns.user.core.config.IntegrationConfig

class UserActivatedEvent(val user: User) : DomainEvent {
    override val eventId: String
        get() = "$channel-$user.id-${System.currentTimeMillis()}"

    override val channel = IntegrationConfig.Channels.USER_STATUS
}
