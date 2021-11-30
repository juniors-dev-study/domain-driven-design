package com.sns.user.component.user.domains

import com.sns.commons.DomainEvent
import com.sns.commons.utils.ifFalse
import com.sns.commons.utils.ifTrue
import com.sns.user.component.user.dtos.FriendRequestedEvent
import com.sns.user.component.user.dtos.FriendshipBrokenEvent
import com.sns.user.component.user.events.UserStatusChangedEvent
import com.sns.user.core.exceptions.AlreadyExistException
import com.sns.user.core.exceptions.NotFoundException
import java.time.Instant
import javax.validation.constraints.Max
import javax.validation.constraints.NotBlank
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Transient
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.MappedCollection
import org.springframework.jdbc.core.DataClassRowMapper
import org.springframework.jdbc.core.RowMapper

data class User(
    @Id
    @NotBlank
    @Max(50)
    @JvmField
    val id: String, // email

    @NotBlank
    @Max(100)
    var password: String,

    @NotBlank
    @Max(50)
    val name: String,

    @NotBlank
    var infoEmailAddress: String = id, // 서비스 정보 수신 이메일주소. 기본값은 id

    @MappedCollection(idColumn = "USER_ID")
    val friends: MutableSet<Friend> = mutableSetOf(),

    @CreatedDate
    val createdAt: Instant = Instant.MIN,

    @LastModifiedDate
    var updatedAt: Instant = Instant.MIN,

    @NotBlank
    var status: Status = Status.ACTIVATED,
) : Persistable<String> {
    @Transient
    private var new: Boolean = false

    override fun getId() = this.id
    override fun isNew() = new

    fun activate(publish: (DomainEvent) -> Unit = { _ -> }) {
        status.checkAlready(Status.ACTIVATED)
        status = Status.ACTIVATED
        publish(UserStatusChangedEvent(this))
    }

    fun delete(publish: (DomainEvent) -> Unit = { _ -> }) {
        // validation if needed
        status.checkAlready(Status.DELETED)
        status = Status.DELETED
        publish(UserStatusChangedEvent(this))
    }

    fun requestFriend(
        receiver: User,
        publish: (DomainEvent) -> Unit = { _ -> }
    ): FriendRequest {
        checkAlreadyFriend(receiver)

        publish(FriendRequestedEvent(this.id, receiver.id))

        return FriendRequest.create(this, receiver)
    }

    fun breakFriendship(
        friendUser: User,
        deletedByFriend: Boolean = false, // 상대 친구에 의해 끊겼는지
        publish: (DomainEvent) -> Unit = { _ -> }
    ) {
        friends.removeIf { it.friendUserId == friendUser.id }
            .ifFalse { throw NotFoundException("친구 관계를 찾을 수 없습니다") }

        publish(FriendshipBrokenEvent(this.id, friendUser.id, deletedByFriend))
    }

    fun friendRequestApproved(receiver: User) {
        addNewFriend(receiver)
    }

    fun friendRequestReceived(requester: User) {
        addNewFriend(requester)
    }

    fun removeFriend(friendUser: User) {
        // TODO exception 규칙 정해지면 대체
        val friend = friends.firstOrNull { it.friendUserId == friendUser.id } ?: throw NoSuchElementException("${friendUser.name} 사용자와 친구 관계가 없습니다")

        friends.remove(friend)
    }

    private fun addNewFriend(user: User) {
        checkAlreadyFriend(user)

        friends += Friend(userId = this.id, friendUserId = user.id)
    }

    private fun checkAlreadyFriend(friendUser: User) {
        friends.any { it.friendUserId == friendUser.id }
            .ifTrue { throw AlreadyExistException("이미 친구 관계인 사용자입니다") }
    }

    companion object {
        val MAPPER: RowMapper<User> = DataClassRowMapper(User::class.java)

        fun create(
            id: String,
            password: String,
            name: String,
            infoEmailAddress: String? = null,
            publish: (DomainEvent) -> Unit = { _ -> }
        ): User {
            // TODO validation
            val user = User(
                id = id,
                password = password,
                name = name,
                infoEmailAddress = infoEmailAddress ?: id,
                status = Status.CREATED,
            ).apply { new = true }

            publish(UserStatusChangedEvent(user))

            return user
        }
    }
}

enum class Status {
    CREATED,
    ACTIVATED,
    DELETED;
    // 비활 등등?

    fun checkAlready(status: Status) {
        if (status == this) throw AlreadyExistException()
    }
}

data class UserId(
    // TODO 적용 예정.
    private val id: String, // email
) {
    fun getEmailAddress() = this.id
    fun getId() = this.id
}
