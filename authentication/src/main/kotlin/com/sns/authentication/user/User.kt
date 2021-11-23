package com.sns.authentication.user

import java.sql.ResultSet
import javax.validation.constraints.Max
import javax.validation.constraints.NotBlank
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.domain.Persistable
import org.springframework.jdbc.core.RowMapper

data class User(
    @Id
    @NotBlank
    @Max(50)
    @JvmField
    val id: String, // email

    @NotBlank
    @Max(100)
    var password: String,

    @NotBlank
    @Max(50)
    val name: String
) : Persistable<String> {
    @Transient
    private var new: Boolean = false

    override fun getId() = this.id
    override fun isNew() = new

    companion object {
        val MAPPER: RowMapper<User> = UserRowMapper()
    }
}

// purpose enum 매핑이 안되서 수동으로 작성함. 확인필요.
class UserRowMapper : RowMapper<User> {
    override fun mapRow(rs: ResultSet, rowNum: Int): User? {
        return User(
            id = rs.getString("id"),
            password = rs.getString("password"),
            name = rs.getString("name"),
        )
    }
}

enum class Status {
    CREATED,
    ACTIVATED,
    DELETED;
}

data class UserId(
    // TODO 적용 예정.
    private val id: String, // email
) {
    public fun getEmailAddress() = this.id
    public fun getId() = this.id
}
