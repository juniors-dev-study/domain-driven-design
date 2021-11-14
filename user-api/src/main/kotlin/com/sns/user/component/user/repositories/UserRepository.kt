package com.sns.user.component.user.repositories

import com.sns.user.component.user.domains.User
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface UserRepository : CrudRepository<User, String> {
    fun findByInfoEmailAddressOrNull(email: String): User?
}
