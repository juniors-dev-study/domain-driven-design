package com.sns.user.component.user.repositories

import com.sns.user.component.user.domains.User
import java.util.*
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface UserRepository : CrudRepository<User, String> {
    fun findByInfoEmailAddress(email: String): Optional<User>
}
