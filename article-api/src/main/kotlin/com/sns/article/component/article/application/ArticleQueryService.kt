package com.sns.article.component.article.application

import com.sns.article.component.article.domains.Article
import com.sns.article.component.article.repositories.ArticleRepository
import org.springframework.stereotype.Service

/**
 * @author Hyounglin Jun
 */
@Service
class ArticleQueryService(
    val articleRepository: ArticleRepository,
) {
    fun getArticles(
        writerUserId: String,
        // TODO 페이징 추가 필요
    ): List<Article> {
        return articleRepository.findAllByWriterUserId(writerUserId)
    }
}
