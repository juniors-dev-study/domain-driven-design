package com.sns.article.endpoints.response

import com.sns.article.component.article.domains.Article
import com.sns.article.component.article.domains.ArticleId
import java.time.Instant
import kotlin.streams.toList

/**
 * 게시물 목록 조회 response
 * @author Hyounglin Jun
 */
data class ArticlesResponse(
    val articles: List<ArticleResponse>,
) {
    companion object {
        fun create(
            list: List<Article>,
        ): ArticlesResponse {
            return ArticlesResponse(
                list.map { ArticleResponse(it) }.toList(),
            )
        }
    }
}
