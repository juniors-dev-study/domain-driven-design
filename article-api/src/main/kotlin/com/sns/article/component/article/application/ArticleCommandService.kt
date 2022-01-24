package com.sns.article.component.article.application

import com.sns.article.component.article.domains.Article
import com.sns.article.component.article.repositories.ArticleRepository
import org.springframework.stereotype.Service

/**
 * @author Hyounglin Jun
 */
@Service
class ArticleCommandService(
    val articleRepository: ArticleRepository,
) {
    fun create(
        userId: String,
        body: String?,
        imageUrls: List<String>?,
    ) {
        val article = Article.create(userId, body, imageUrls)
        articleRepository.save(article)
    }
}
