package com.sns.user.component.user.domains

import com.sns.commons.DomainEvent
import com.sns.user.component.user.dtos.FriendRequestApprovedEvent
import com.sns.user.component.user.dtos.FriendRequestRejectedEvent
import java.time.Instant
import javax.validation.constraints.NotBlank
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Persistent

@Persistent
data class FriendRequest(
    @Id
    val id: Long = 0,

    @NotBlank
    val requesterId: String,

    @NotBlank
    val receiverId: String,

    @CreatedDate
    val createdAt: Instant = Instant.MIN
) {
    fun approve(
        actorUserId: String,
        action: FriendRequest.() -> Unit,
        publish: (DomainEvent) -> Unit = { _ -> }
    ) {
        if (receiverId != actorUserId) {
            throw IllegalArgumentException("친구 요청을 받은 사용자만 수락할 수 있습니다")
        }

        this.action()

        publish(FriendRequestApprovedEvent(requesterId, receiverId))
    }

    fun reject(
        actorUserId: String,
        publish: (DomainEvent) -> Unit = { _ -> }
    ) {
        if (receiverId != actorUserId) {
            throw IllegalArgumentException("친구 요청을 받은 사용자만 거절할 수 있습니다")
        }

        publish(FriendRequestRejectedEvent(requesterId, receiverId))
    }

    companion object {
        fun create(
            requester: User,
            receiver: User
        ): FriendRequest = FriendRequest(
            requesterId = requester.id,
            receiverId = receiver.id,
        )
    }
}
