package com.sns.user.component.user.repositories

import com.sns.user.component.user.domains.FriendRequest
import java.util.*
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface FriendRequestRepository : CrudRepository<FriendRequest, Int> {
    fun findByRequesterIdAndReceiverId(requesterId: String, receiverId: String): Optional<FriendRequest>
}
