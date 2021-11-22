package com.sns.user.components.user.application

import com.sns.user.component.user.application.UserFriendService
import com.sns.user.component.user.repositories.UserRepository
import com.sns.user.createUser
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class UserFriendServiceTest {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var userFriendService: UserFriendService

    @Test
    fun createFriendRequest() {
        val id = "testAccount@n2ver.com"
        val id2 = "testAccount2@n2ver.com"

        val user = createUser(id)
        val user2 = createUser(id2)

        userRepository.saveAll(listOf(user, user2))

        userFriendService.createFriendRequest(id, id2)
    }

    @Test
    fun approveFriendRequest() {
        val id = "testAccount@n2ver.com"
        val id2 = "testAccount2@n2ver.com"

        val user = createUser(id)
        val user2 = createUser(id2)

        userRepository.saveAll(listOf(user, user2))
        val friendRequest = userFriendService.createFriendRequest(id, id2)

        userFriendService.approveFriendRequest(id2, friendRequest.id)
    }

    @Test
    fun rejectFriendRequest() {
        val id = "testAccount@n2ver.com"
        val id2 = "testAccount2@n2ver.com"

        val user = createUser(id)
        val user2 = createUser(id2)

        userRepository.saveAll(listOf(user, user2))
        val friendRequest = userFriendService.createFriendRequest(id, id2)

        userFriendService.rejectFriendRequest(id2, friendRequest.id)
    }
}
