package com.sns.user.component.user.repositories

import com.sns.user.component.user.domains.Profile
import com.sns.user.hasValueSatisfying
import com.sns.user.isEqualTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

/**
 * @author Hyounglin Jun
 */
@SpringBootTest
internal class ProfileRepositoryTest {
    @Autowired
    lateinit var profileRepository: ProfileRepository

    @Transactional
    @Test
    fun save() {
        val id = "test@gmail.com"
        val profile = Profile.create(
            userId = id,
        )

        profileRepository.save(profile)

        profileRepository.findById(id) hasValueSatisfying { savedUser ->
            savedUser.userId isEqualTo id
        }
    }

    @Test
    fun save_for_update() {
        val id = "test@gmail.com"
        val updatedNickName = "닉네임_변경"
        val hobbies = listOf("밥먹기", "운동하기")

        profileRepository.save(
            Profile.create(
                userId = id,
            ),
        )

        profileRepository.save(
            Profile.update(
                userId = id,
                nickName = updatedNickName,
                hobbies = hobbies,
            ),
        )

        profileRepository.findById(id) hasValueSatisfying { savedUser ->
            savedUser.userId isEqualTo id
            savedUser.nickName isEqualTo updatedNickName
            savedUser.iconImageUrl isEqualTo null
            savedUser.intro isEqualTo null
            savedUser.hobbies isEqualTo hobbies
        }
    }
}




