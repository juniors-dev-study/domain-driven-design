package com.sns.article.component.article.application

import com.sns.article.component.article.repositories.ArticleRepository
import com.sns.article.component.comment.application.CommentCommandService
import com.sns.article.component.comment.domains.RootType
import com.sns.article.component.comment.repositories.CommentRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class ArticleCommandServiceTest {

    @Autowired
    lateinit var articleRepository: ArticleRepository

    @Autowired
    lateinit var commentRepository: CommentRepository

    @Autowired
    lateinit var commentCommandService: CommentCommandService

    @Autowired
    lateinit var articleCommandService: ArticleCommandService

    @Test
    fun delete() {
        val writerId = "userId"
        val article = articleCommandService.create(writerId, "내용")
        assertThat(articleRepository.findByIdOrNull(article.articleId!!)).isNotNull

        val comment = commentCommandService.create(RootType.ARTICLE, article.articleId!!.id.toString(), "댓글 내용", writerId)
        assertThat(commentRepository.findByIdOrNull(comment.id!!)).isNotNull

        articleCommandService.delete(writerId, article.articleId!!)

        assertThat(articleRepository.findByIdOrNull(article.articleId!!)).isNull()
        assertThat(commentRepository.findByIdOrNull(comment.id!!)).isNull()
    }
}
