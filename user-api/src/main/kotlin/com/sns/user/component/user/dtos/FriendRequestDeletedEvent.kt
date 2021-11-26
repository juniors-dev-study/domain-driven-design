package com.sns.user.component.user.dtos

import com.sns.commons.DomainEvent
import com.sns.user.core.config.IntegrationConfig

class FriendRequestDeletedEvent(
    val requesterId: String,
    val receiverId: String,
) : DomainEvent(IntegrationConfig.Channels.FRIEND_REQUEST, "$requesterId-$receiverId")
