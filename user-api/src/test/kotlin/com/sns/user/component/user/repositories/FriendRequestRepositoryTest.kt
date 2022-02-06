package com.sns.user.component.user.repositories

import com.sns.user.component.user.domains.FriendRequest
import com.sns.user.isNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class FriendRequestRepositoryTest {

    @Autowired
    lateinit var friendRequestRepository: FriendRequestRepository

    @Test
    fun findByRequesterIdAndReceiverId() {
        val requesterId = "requester"
        val receiverId = "receiver"

        val friendRequest = FriendRequest(requesterId = requesterId, receiverId = receiverId)

        friendRequestRepository.save(friendRequest)

        friendRequestRepository.findByRequesterIdAndReceiverId(requesterId, receiverId).isNotNull()
    }
}
