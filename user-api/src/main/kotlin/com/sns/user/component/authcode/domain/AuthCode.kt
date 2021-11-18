package com.sns.user.component.authcode.domain

import kotlin.random.Random
import java.sql.ResultSet
import java.time.Instant
import javax.validation.constraints.NotBlank
import org.springframework.jdbc.core.RowMapper

data class AuthCode(
    @NotBlank
    val purpose: Purpose,
    @NotBlank
    val userId: String,
    @NotBlank
    val code: String = (1..CODE_LENGTH)
        .map { Random.nextInt(0, charPool.size) }
        .map(charPool::get)
        .joinToString(""),
    val createdAt: Instant = Instant.MIN
) {

    fun isCorrect(userId: String, code: String, purpose: Purpose): Boolean =
        (this.userId == userId) and (this.code == code) and (this.purpose == purpose)

    companion object {
        private const val CODE_LENGTH = 10;
        private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        fun createSignUp(userId: String) = AuthCode(purpose = Purpose.SIGN_UP, userId = userId)
        val MAPPER: RowMapper<AuthCode> = AuthCodeRowMapper()
    }
}

data class AuthCodeKey(
    @NotBlank
    val purpose: Purpose,
    @NotBlank
    val userId: String,
) {
    fun toMap(): MutableMap<String, Any> = mutableMapOf(
        "userId" to userId,
        "purpose" to purpose.name,
    )
}

// purpose enum 매핑이 안되서 수동으로 작성함. 확인필요.
class AuthCodeRowMapper : RowMapper<AuthCode> {
    override fun mapRow(rs: ResultSet, rowNum: Int): AuthCode? {
        return AuthCode(
            purpose = Purpose.valueOf(rs.getString("purpose")),
            userId = rs.getString("user_id"),
            code = rs.getString("code"),
            createdAt = Instant.ofEpochMilli(rs.getTimestamp("created_at").time),
        )
    }
}

enum class Purpose {
    SIGN_UP
}
