package com.sns.article.component.article.repositories

import com.sns.article.component.article.domains.Article
import com.sns.article.component.article.domains.ArticleId
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

/**
 * @author Hyounglin Jun
 */
@Repository
interface ArticleRepository : CrudRepository<Article, ArticleId> {
    fun findAllByWriterUserId(writerUserId: String): Optional<List<Article>>
}
