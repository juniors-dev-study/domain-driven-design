package com.sns.user.component.user.application

import com.sns.user.component.user.domains.Profile
import com.sns.user.component.user.repositories.ProfileRepository
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
}
