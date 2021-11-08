package com.sns.user.components.user.repositories

import com.sns.user.component.user.domains.User
import com.sns.user.component.user.repositories.UserRepository
import com.sns.user.hasValueSatisfying
import com.sns.user.isEqualTo
import com.sns.user.isNotEqualTo
import java.time.Instant
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    fun save() {
        val id = "testAccount@n2ver.com"
        val name = "TESTER"

        val user = User.create(id, "1235", name)

        userRepository.save(user)

        userRepository.findById(id) hasValueSatisfying { savedUser ->
            savedUser.id isEqualTo id
            savedUser.name isEqualTo name
            savedUser.infoEmailAddress isEqualTo id
            savedUser.createdAt isNotEqualTo Instant.MIN
            savedUser.updatedAt isNotEqualTo Instant.MIN
        }
    }
}
