package com.sns.user.component.user.repositories

import com.sns.user.component.user.domains.User
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class DefaultUserRepository(
    userCrudRepository: UserCrudRepository,
    private val jdbcTemplate: JdbcTemplate
) : UserRepository,
    UserCrudRepository by userCrudRepository {

    override fun findByInfoEmailAddressOrNull(email: String): User? = jdbcTemplate.queryForObject(
        """
            SELECT `id`,`password`,`name`,info_email_address,created_at,updated_at,`status`
            FROM `user`
            WHERE info_email_address = ?
            LIMIT 1
        """.trimIndent(),
        User.MAPPER, email,
    )
}
