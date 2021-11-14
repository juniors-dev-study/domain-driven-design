package com.sns.user.component.user.repositories

import com.sns.user.component.user.domains.FriendRequest
import java.util.*
import org.springframework.data.repository.CrudRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class DefaultFriendRequestRepository(
    private val jdbcTemplate: JdbcTemplate,
    private val friendRequestCrudRepository: FriendRequestCrudRepository
) : FriendRequestRepository,
    CrudRepository<FriendRequest, Int> by friendRequestCrudRepository {
    override fun findByRequesterIdAndReceiverId(requesterId: String, receiverId: String): Optional<FriendRequest> =
        friendRequestCrudRepository.findByRequesterIdAndReceiverId(requesterId, receiverId)
}
