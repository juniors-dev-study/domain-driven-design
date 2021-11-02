package com.sns.user.component.test.dtos

import com.sns.commons.DomainEvent
import com.sns.user.core.config.IntegrationConfig

class LaughingEvent(val who: String) : DomainEvent {
    // FIMXE 예제용 임시 포맷
    override val eventId: String
        get() = "$channel-$who-${System.currentTimeMillis()}"

    override val channel = IntegrationConfig.Channels.EMOTION
}
