package com.sns.user.component.user.listeners

import com.sns.commons.annotation.CustomEventListener
import com.sns.commons.utils.log
import com.sns.user.component.user.dtos.FriendRequestApprovedEvent
import com.sns.user.component.user.dtos.FriendRequestRejectedEvent
import com.sns.user.component.user.dtos.FriendRequestedEvent

@CustomEventListener
class FriendListener {
    private val log = log()

    fun friendRequested(friendRequestedEvent: FriendRequestedEvent) {
        // TODO 알림을 보내거나 기타 기능 추가
        log.info("친구요청: {} -> {}", friendRequestedEvent.requesterId, friendRequestedEvent.receiverId)
    }

    fun friendRequestApproved(friendRequestApprovedEvent: FriendRequestApprovedEvent) {
        // TODO 알림을 보내거나 기타 기능 추가
        log.info("친구요청 승인: {} -> {}", friendRequestApprovedEvent.requesterId, friendRequestApprovedEvent.receiverId)
    }

    fun friendRequestRejected(friendRequestRejectedEvent: FriendRequestRejectedEvent) {
        // TODO 알림을 보내거나 기타 기능 추가
        log.info("친구요청 거절: {} -> {}", friendRequestRejectedEvent.requesterId, friendRequestRejectedEvent.receiverId)
    }
}
