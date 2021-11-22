package com.sns.user.component.user.application

import com.sns.user.component.user.domains.Profile
import com.sns.user.component.user.repositories.ProfileRepository
import org.springframework.stereotype.Service
import java.util.*

/**
 * @author Hyounglin Jun
 */
@Service
class ProfileQueryService(
    val profileRepository: ProfileRepository,
) {
    fun getById(userId: String): Optional<Profile> = profileRepository.findById(userId)
}
