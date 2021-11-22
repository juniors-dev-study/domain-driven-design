package com.sns.user.component.user.dtos

import com.sns.commons.DomainEvent
import com.sns.user.core.config.IntegrationConfig

class FriendshipBrokenEvent(
    val userId: String,
    val friendUserId: String,
    val deletedByFriend: Boolean,
) : DomainEvent(IntegrationConfig.Channels.FRIEND_REQUEST, "$userId-$friendUserId")
