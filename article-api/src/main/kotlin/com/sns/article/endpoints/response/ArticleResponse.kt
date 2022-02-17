package com.sns.article.endpoints.response

import com.sns.article.component.article.domains.Article
import com.sns.article.component.article.domains.ArticleId
import java.time.Instant
import kotlin.streams.toList

/**
 * 게시물 조회 response
 * @author Hyounglin Jun
 */
data class ArticleResponse(
    val articleId: ArticleId?,
    val imageUrls: MutableList<String>?,
    val body: String?,
    val writerUserId: String,
    var updatedAt: Instant,
    val createdAt: Instant,
) {
    constructor(article: Article) : this(
        articleId = article.articleId,
        imageUrls = article.imageUrls,
        body = article.body,
        writerUserId = article.writerUserId,
        updatedAt = article.updatedAt,
        createdAt = article.createdAt,
    )
}
