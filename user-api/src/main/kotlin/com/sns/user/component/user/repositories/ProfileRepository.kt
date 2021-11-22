package com.sns.user.component.user.repositories

import com.sns.user.component.user.domains.Profile
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * @author Hyounglin Jun
 */
@Repository
interface ProfileRepository : CrudRepository<Profile, String>
