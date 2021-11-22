package com.sns.user.component.user.domains

import java.time.Instant
import java.util.*
import javax.validation.constraints.NotBlank
import org.springframework.data.annotation.CreatedDate

class Friend(
    @NotBlank
    val userId: String,

    @NotBlank
    val friendUserId: String,

    @CreatedDate
    val createdAt: Instant = Instant.MIN
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Friend

        if (userId != other.userId || friendUserId != other.friendUserId) return false

        return true
    }

    override fun hashCode(): Int {
        return Objects.hashCode("$userId$friendUserId")
    }
}
