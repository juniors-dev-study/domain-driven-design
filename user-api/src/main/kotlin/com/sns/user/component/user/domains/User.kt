package com.sns.user.component.user.domains

import java.time.Instant
import javax.validation.constraints.Max
import javax.validation.constraints.NotBlank
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Transient
import org.springframework.data.domain.Persistable

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
) : Persistable<String> {
    @Transient
    private var new: Boolean = false

    override fun getId() = this.id
    override fun isNew() = new

    companion object {
        fun create(
            id: String,
            password: String,
            name: String,
            infoEmailAddress: String? = null
        ): User {
            // TODO validation
            return User(
                id = id,
                password = password, // TODO encrypt
                name = name,
                infoEmailAddress = infoEmailAddress ?: id,
            ).apply { new = true }
        }
    }
}
