package com.sns.user.component.authcode.repositories

import com.sns.user.component.authcode.domain.AuthCode
import com.sns.user.component.authcode.domain.Purpose
import org.springframework.data.repository.CrudRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class DefaultAuthCodeRepository(
    val jdbcTemplate: JdbcTemplate,
    val authCodeCrudRepository: AuthCodeCrudRepository
) : AuthCodeRepository, CrudRepository<AuthCode, Int> by authCodeCrudRepository {

    override fun findByUserIdAndPurpose(userId: String, purpose: Purpose): AuthCode? = jdbcTemplate.queryForObject(
        """
            SELECT id,user_id,`code`,created_at,purpose
            FROM auth_code
           WHERE user_id = ? AND purpose = ?
           ORDER BY id DESC
          LIMIT 1
        """.trimIndent(),
        AuthCode.MAPPER, userId, purpose.name,
    )
}
