package com.sns.user.component.user.events

import com.sns.commons.DomainEvent
import com.sns.user.component.user.domains.User
import com.sns.user.core.config.IntegrationConfig

class UserStatusChangedEvent(val user: User) : DomainEvent(IntegrationConfig.Channels.USER_STATUS, user.id) {
}
