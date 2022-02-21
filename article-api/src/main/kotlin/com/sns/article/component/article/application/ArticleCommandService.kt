package com.sns.article.component.article.application

import com.sns.article.component.article.domains.Article
import com.sns.article.component.article.domains.ArticleId
import com.sns.article.component.article.domains.ArticleScope
import com.sns.article.component.article.repositories.ArticleRepository
import com.sns.article.component.comment.domains.Comment
import com.sns.article.component.comment.repositories.CommentRepository
import com.sns.article.component.reaction.domains.ReactionRepository
import com.sns.commons.exceptions.NoAuthorityException
import com.sns.commons.exceptions.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author Hyounglin Jun
 */
@Transactional
@Service
class ArticleCommandService(
    val articleRepository: ArticleRepository,
    val reactionRepository: ReactionRepository,
    val commentRepository: CommentRepository,
) {
    fun create(
        userId: String,
        body: String?,
        imageUrls: List<String>? = null,
        scope: ArticleScope = ArticleScope.PUBLIC,
    ): Article {
        val article = Article.create(userId, body, imageUrls, scope)
        return articleRepository.save(article)
    }

    fun modify(
        articleId: ArticleId,
        userId: String,
        body: String?,
        imageUrls: List<String>? = null,
    ): Article {
        val article = articleRepository.findById(articleId).orElseThrow { NotFoundException("작성한 글이 없습니다.") }
        if (article.writerUserId != userId) throw NoAuthorityException("작성한 글과 작성자가 일치하지 않습니다.")

        val modifiedArticle = article.modify(body, imageUrls)
        return articleRepository.save(modifiedArticle)
    }

    fun delete(
        userId: String,
        articleId: ArticleId,
    ) {
        val article = articleRepository.findById(articleId).orElseThrow {
            NotFoundException("이미 삭제되었거나, 없는 게시글입니다.")
        }

        articleRepository.delete(article)
        commentRepository.findAllByRootIdInAndRootType(listOf(articleId.id.toString()), Comment.Root.Type.ARTICLE)
            .let { commentRepository.deleteAll(it) }
        // FIXME reaction 을 삭제하는 방법 확인 (DB 정의가 없음)
    }
}
