package com.sns.article.component.comment.domains

import com.sns.commons.exceptions.NoAuthorityException
import com.sns.commons.utils.ifTrue
import java.time.Instant
import javax.validation.constraints.Max
import javax.validation.constraints.NotBlank
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate

fun String.assertNotBlank() = assert(this.isNotBlank()) { "내용이 없습니다." }

data class Comment(
    @Id
    @NotBlank
    @Max(50)
    @JvmField
    var id: Long? = null,

    @NotBlank
    val rootType: RootType,

    @NotBlank
    val rootId: String,

    @NotBlank
    var contents: String,

    @NotBlank
    val writerId: String,

    @CreatedDate
    var createdAt: Instant = Instant.MIN,

    @LastModifiedDate
    var updatedAt: Instant = Instant.MIN,
) {
    fun update(contents: String, userId: String) {
        checkAuth(userId)
        contents.assertNotBlank()

        this.contents = contents
        this.updatedAt = Instant.now()
    }

    fun checkAuth(userId: String) = (userId != writerId).ifTrue { throw NoAuthorityException() }

    companion object {
        fun create(rootType: RootType, rootId: String, contents: String, writerId: String): Comment {
            contents.assertNotBlank()

            return Comment(rootType = rootType, rootId = rootId, contents = contents, writerId = writerId)
        }
    }
}

enum class RootType {
    ARTICLE, COMMENT
}
