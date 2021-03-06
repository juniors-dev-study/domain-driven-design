package com.sns.user.component.user.repositories

import com.sns.user.createUser
import com.sns.user.hasValueSatisfying
import com.sns.user.isEqualTo
import com.sns.user.isNotEmpty
import com.sns.user.isNotEqualTo
import com.sns.user.satisfies
import java.time.Instant
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class UserRepositoryTest {
    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    fun save() {
        val id = "testAccount@n2ver.com"

        val user = createUser(id)

        userRepository.save(user)

        userRepository.findById(id) hasValueSatisfying { savedUser ->
            savedUser.id isEqualTo id
            savedUser.infoEmailAddress isEqualTo id
            savedUser.createdAt isNotEqualTo Instant.MIN
            savedUser.updatedAt isNotEqualTo Instant.MIN
        }
    }

    @Test
    internal fun findByInfoEmailAddress() {
        userRepository.findByInfoEmailAddress("dev@gm1.com").orElseGet(null)!! satisfies { it.name isEqualTo "김개발" }
    }

    @DisplayName("신규 친구 추가 저장 테스트")
    @Test
    fun save2() {
        val id = "testAccount@n2ver.com"
        val id2 = "testAccount2@n2ver.com"

        val user = createUser(id)
        val user2 = createUser(id2)

        user.friendRequestReceived(user2)

        userRepository.save(user)

        userRepository.findById(id) hasValueSatisfying { savedUser ->
            savedUser.id isEqualTo id
            savedUser.infoEmailAddress isEqualTo id
            savedUser.friends.isNotEmpty().anyMatch { it.friendUserId == user2.id }
            savedUser.createdAt isNotEqualTo Instant.MIN
            savedUser.updatedAt isNotEqualTo Instant.MIN
        }
    }
}
