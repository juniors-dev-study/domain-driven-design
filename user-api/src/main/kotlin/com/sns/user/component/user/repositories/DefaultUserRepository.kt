package com.sns.user.component.user.repositories

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class DefaultUserRepository(
    userCrudRepository: UserCrudRepository,
    private val jdbcTemplate: JdbcTemplate
) : UserRepository,
    UserCrudRepository by userCrudRepository
