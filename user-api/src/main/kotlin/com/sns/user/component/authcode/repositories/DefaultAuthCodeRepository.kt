package com.sns.user.component.authcode.repositories

import com.sns.user.component.authcode.domain.AuthCode
import com.sns.user.component.authcode.domain.Purpose
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class DefaultAuthCodeRepository(
    val jdbcTemplate: NamedParameterJdbcTemplate,
) : AuthCodeRepository {

    override fun findByUserIdAndPurpose(userId: String, purpose: Purpose): AuthCode? = jdbcTemplate.queryForObject(
        """
          SELECT user_id,`code`,created_at,purpose
          FROM auth_code
          WHERE user_id = :userId AND purpose = :purpose
          LIMIT 1
        """.trimIndent(),
        mutableMapOf(
            "userId" to userId,
            "purpose" to purpose.name,
        ),
        AuthCode.MAPPER,
    )

    @Transactional
    override fun save(authCode: AuthCode): AuthCode {
        jdbcTemplate.update(
            """
            REPLACE INTO auth_code (user_id, `code`, created_at, purpose)
            VALUES (:userId, :code, NOW(), :purpose)
        """.trimIndent(),
            mutableMapOf(
                "userId" to authCode.userId,
                "purpose" to authCode.purpose.name,
                "code" to authCode.code,
            ),
        )
        return authCode
    }
}
