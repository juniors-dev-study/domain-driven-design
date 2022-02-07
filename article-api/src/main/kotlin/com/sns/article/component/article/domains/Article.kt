package com.sns.article.component.article.domains

import org.hibernate.validator.constraints.URL
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Transient
import org.springframework.data.domain.Persistable
import java.time.Instant
import javax.validation.constraints.Max
import javax.validation.constraints.NotBlank

/**
 * @author Hyounglin Jun
 */
data class Article(
    @Id
    @NotBlank
    val articleId: ArticleId?,

    @Max(100)
    @URL
    val imageUrls: MutableList<String>?,

    @Max(1000)
    val body: String?,

    @NotBlank
    val writerUserId: String,

    @LastModifiedDate
    var updatedAt: Instant = Instant.MIN,

    @CreatedDate
    val createdAt: Instant = Instant.MIN,
) : Persistable<ArticleId> {
    companion object {
        fun create(
            writerUserId: String,
            body: String?,
            imageUrls: List<String>?,
        ): Article {
            return Article(
                articleId = null,
                writerUserId = writerUserId,
                imageUrls = imageUrls?.toMutableList(),
                body = body,
            ).apply { new = true }
        }

        fun modify(
            articleId: ArticleId,
            writerUserId: String,
            body: String?,
            imageUrls: List<String>?,
        ): Article {
            return Article(
                articleId = articleId,
                writerUserId = writerUserId,
                imageUrls = imageUrls?.toMutableList(),
                body = body,
            ).apply { new = false }
        }
    }

    @Transient
    private var new: Boolean = false

    override fun isNew() = new
    override fun getId() = this.articleId
}

data class ArticleId(
    val id: Int,
)
