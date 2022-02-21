package com.sns.article.component.article.application

import com.sns.article.component.article.domains.Article
import com.sns.article.component.article.domains.ArticleId
import com.sns.article.component.article.domains.ArticleScope
import com.sns.article.component.article.repositories.ArticleRepository
import com.sns.commons.exceptions.NoAuthorityException
import com.sns.commons.exceptions.NotFoundException
import java.time.Instant
import org.springframework.stereotype.Service

/**
 * @author Hyounglin Jun
 */
@Service
class ArticleQueryService(
    val articleRepository: ArticleRepository,
) {
    fun getMyArticle(
        writerUserId: String,
        articleId: ArticleId
    ): Article {
        val article = articleRepository.findById(articleId).orElseThrow { throw NotFoundException("해당 게시물이 없습니다.") }
        if (article.writerUserId != writerUserId) throw NoAuthorityException("작성자가 아닙니다.")
        return article
    }

    fun getArticles(
        writerUserId: String,
        updatedAt: Instant = Instant.now(),
    ): List<Article> {
        return articleRepository.findTop100ByWriterUserIdAndUpdatedAtBefore(writerUserId, updatedAt)
    }

    fun getFriendsArticles(
        friendUserIds: List<String>,
        updatedAt: Instant,
    ): List<Article> {
        return articleRepository.findTop100ByWriterUserIdInAndUpdatedAtBeforeAndScopeIn(
            friendUserIds,
            updatedAt,
            setOf(ArticleScope.FRIEND, ArticleScope.PUBLIC),
        )
    }
}
