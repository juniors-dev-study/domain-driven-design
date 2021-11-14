package com.sns.user.component.user.domains

import com.sns.commons.DomainEvent
import com.sns.user.component.user.events.UserStatusChangedEvent
import com.sns.user.core.exceptions.AlreadyExistException
import java.sql.ResultSet
import java.time.Instant
import javax.validation.constraints.Max
import javax.validation.constraints.NotBlank
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
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
    val name: String,

    @NotBlank
    var infoEmailAddress: String = id, // 서비스 정보 수신 이메일주소. 기본값은 id

    @CreatedDate
    val createdAt: Instant = Instant.MIN,

    @LastModifiedDate
    var updatedAt: Instant = Instant.MIN,

    @NotBlank
    var status: Status = Status.ACTIVATED
) : Persistable<String> {
    @Transient
    private var new: Boolean = false

    override fun getId() = this.id
    override fun isNew() = new

    fun activate(publish: (DomainEvent) -> Unit = { _ -> }) {
        status.checkAlready(Status.ACTIVATED)
        status = Status.ACTIVATED
        publish(UserStatusChangedEvent(this))
    }

    companion object {
        val MAPPER: RowMapper<User> = UserRowMapper()

        fun create(
            id: String,
            password: String,
            name: String,
            infoEmailAddress: String? = null,
            publish: (DomainEvent) -> Unit = { _ -> }
        ): User {
            // TODO validation
            val user = User(
                id = id,
                password = password,
                name = name,
                infoEmailAddress = infoEmailAddress ?: id,
                status = Status.CREATED,
            ).apply { new = true }

            publish(UserStatusChangedEvent(user))

            return user
        }
    }
}

// purpose enum 매핑이 안되서 수동으로 작성함. 확인필요.
class UserRowMapper : RowMapper<User> {
    override fun mapRow(rs: ResultSet, rowNum: Int): User? {
        return User(
            id = rs.getString("id"),
            password = rs.getString("password"),
            name = rs.getString("name"),
            infoEmailAddress = rs.getString("info_email_address"),
            status = Status.valueOf(rs.getString("status")),
            createdAt = Instant.ofEpochMilli(rs.getTimestamp("created_at").time),
            updatedAt = Instant.ofEpochMilli(rs.getTimestamp("updated_at").time),
        )
    }
}

enum class Status {
    CREATED,
    ACTIVATED;
    // 비활 등등?

    fun checkAlready(status: Status) {
        if (status == this) throw AlreadyExistException()
    }
}
