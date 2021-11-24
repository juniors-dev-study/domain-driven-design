package com.sns.user.component.user.repositories

import com.sns.user.component.user.domains.Profile
import com.sns.user.core.config.db.converter.EntityList
import com.sns.user.hasValueSatisfying
import com.sns.user.isEqualTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

/**
 * @author Hyounglin Jun
 */
@SpringBootTest
internal class ProfileRepositoryTest {
    @Autowired
    lateinit var profileRepository: ProfileRepository

    @Test
    fun save() {
        val id = "test@gmail.com"
        val nickName = "닉네임"
        val inputHobbies = listOf("밥먹기", "운동하기")
        val outputHobbies = EntityList(listOf("밥먹기", "운동하기"))
        val profile = Profile.create(
            userId = id, nickName = nickName,
            hobbies = inputHobbies,
        )

        profileRepository.save(profile)

        profileRepository.findById(id) hasValueSatisfying { savedUser ->
            savedUser.userId isEqualTo id
            savedUser.nickName isEqualTo nickName
            savedUser.iconImageUrl isEqualTo null
            savedUser.intro isEqualTo null
            savedUser.hobbies isEqualTo outputHobbies
        }
    }
}




