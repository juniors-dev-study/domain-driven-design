package com.sns.user.component.user.application

import com.sns.user.component.user.repositories.ProfileRepository
import com.sns.user.hasValueSatisfying
import com.sns.user.isEqualTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

/**
 * @author Hyounglin Jun
 */
@SpringBootTest
internal class ProfileCommandServiceTest {

    @Autowired
    lateinit var profileRepository: ProfileRepository

    @Autowired
    lateinit var profileCommandService: ProfileCommandService

    @Test
    fun update() {
        // given
        val userId = "test@naver.com"
        profileCommandService.create(userId)

        // when
        val updatedNickName = "닉네임"
        profileCommandService.update(userId, updatedNickName)

        // then
        profileRepository.findById(userId) hasValueSatisfying {
            it.userId isEqualTo userId
            it.nickName isEqualTo updatedNickName
        }
    }
}
