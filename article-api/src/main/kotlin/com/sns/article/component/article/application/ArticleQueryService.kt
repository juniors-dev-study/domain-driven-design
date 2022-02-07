package com.sns.article.component.article.application

import com.sns.article.component.article.domains.Article
import com.sns.article.component.article.domains.ArticleId
import com.sns.article.component.article.repositories.ArticleRepository
import com.sns.commons.exceptions.NoAuthorityException
import com.sns.commons.exceptions.NotFoundException
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
        // TODO 페이징 추가 필요
    ): List<Article> {
        return articleRepository.findAllByWriterUserId(writerUserId)
    }
}
