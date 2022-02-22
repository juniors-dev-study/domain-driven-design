package com.sns.article.component.article.repositories

import com.sns.article.component.article.domains.Article
import com.sns.article.component.article.domains.ArticleId
import com.sns.article.component.article.domains.ArticleScope
import java.time.Instant
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * @author Hyounglin Jun
 */
@Repository
interface ArticleCrudRepository : CrudRepository<Article, ArticleId> {
    fun findTop100ByWriterUserIdAndUpdatedAtBeforeOrderByUpdatedAtDesc(writerUserId: String, updatedAt: Instant): List<Article>

    fun findTop100ByWriterUserIdInAndUpdatedAtBeforeAndScopeInOrderByUpdatedAtDesc(
        userIds: List<String>,
        updatedAt: Instant,
        scope: Set<ArticleScope>
    ): List<Article>
}
