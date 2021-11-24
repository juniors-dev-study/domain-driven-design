package com.sns.user.component.user.events

import com.sns.commons.DomainEvent
import com.sns.user.component.user.domains.User

class UserStatusChangedEvent(val user: User) : DomainEvent("", "") {

}
