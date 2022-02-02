package com.sns.user.component.user.application

import com.sns.user.component.user.domains.Profile
import com.sns.user.component.user.repositories.ProfileRepository
import com.sns.commons.exceptions.NoAuthorityException
import org.springframework.stereotype.Service

/**
 * @author Hyounglin Jun
 */
@Service
class ProfileCommandService(
    val profileRepository: ProfileRepository,
) {
    fun create(
        userId: String,
    ): Profile {

        val profile = Profile.create(userId)

        profileRepository.save(profile)
        return profile
    }

    fun update(
        userId: String,
        nickName: String? = null,
        iconImageUrl: String? = null,
        intro: String? = null,
        hobbies: MutableList<String>? = null,
    ): Profile {
        profileRepository.findById(userId).orElseThrow {
            NoAuthorityException()
        }
        val profile = Profile.update(userId, nickName, iconImageUrl, intro, hobbies)
        profileRepository.save(profile)
        return profile
    }
}
