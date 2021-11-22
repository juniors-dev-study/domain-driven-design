package com.sns.user.component.test.dtos

import com.sns.commons.DomainEvent
import com.sns.user.core.config.IntegrationConfig

class LaughingEvent(val who: String) : DomainEvent(IntegrationConfig.Channels.EMOTION, who)
