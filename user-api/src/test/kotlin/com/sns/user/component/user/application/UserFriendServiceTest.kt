package com.sns.user.component.user.application

import com.sns.commons.exceptions.NotFoundException
import com.sns.user.component.user.repositories.UserRepository
import com.sns.user.createUser
import com.sns.user.isNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
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
    fun breakFriendship() {
        val id = "testAccount@n2ver.com"
        val id2 = "testAccount2@n2ver.com"

        val user = createUser(id)
        val user2 = createUser(id2)

        userRepository.saveAll(listOf(user, user2))
        val friendRequest = userFriendService.createFriendRequest(id, id2)

        userFriendService.approveFriendRequest(id2, friendRequest.id)

        assertDoesNotThrow {
            userFriendService.breakFriendship(id, id2)
        }
    }

    @Test
    fun createFriendRequest() {
        val id = "testAccount@n2ver.com"
        val id2 = "testAccount2@n2ver.com"

        val user = createUser(id)
        val user2 = createUser(id2)

        userRepository.saveAll(listOf(user, user2))

        userFriendService.createFriendRequest(id, id2).isNotNull()
    }

    @Test
    fun approveFriendRequest() {
        val id = "testAccount@n2ver.com"
        val id2 = "testAccount2@n2ver.com"

        val user = createUser(id)
        val user2 = createUser(id2)

        userRepository.saveAll(listOf(user, user2))
        val friendRequest = userFriendService.createFriendRequest(id, id2)

        assertDoesNotThrow {
            userFriendService.approveFriendRequest(id2, friendRequest.id)
        }

        assertThrows<NotFoundException>("친구 요청을 수락한 후에는 기존 친구 요청이 존재하지 않는다") {
            userFriendService.approveFriendRequest(id2, friendRequest.id)
        }
    }

    @Test
    fun rejectFriendRequest() {
        val id = "testAccount@n2ver.com"
        val id2 = "testAccount2@n2ver.com"

        val user = createUser(id)
        val user2 = createUser(id2)

        userRepository.saveAll(listOf(user, user2))
        val friendRequest = userFriendService.createFriendRequest(id, id2)

        assertDoesNotThrow {
            userFriendService.rejectFriendRequest(id2, friendRequest.id)
        }
    }
}
