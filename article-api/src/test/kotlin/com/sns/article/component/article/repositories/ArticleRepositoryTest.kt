package com.sns.article.component.article.repositories

import com.sns.article.component.article.domains.Article
import com.sns.article.component.article.domains.ArticleScope
import java.time.Instant
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

/**
 * @author Hyounglin Jun
 */
@SpringBootTest
internal class ArticleRepositoryTest {
    @Autowired
    lateinit var articleRepository: ArticleRepository

    @Test
    fun save() {
        // given
        val writerUserId = "test@naver.com"
        val body = "본문입니다."
        val imageUrls = listOf("http://aaa.com", "http://bbb.com")
        val article = Article.create(writerUserId, body, imageUrls, ArticleScope.PUBLIC)

        // when
        val writtenArticle = articleRepository.save(article)

        // then
        val foundArticle = articleRepository.findById(writtenArticle.articleId ?: throw Exception()).orElseThrow()
        assertEquals(writtenArticle, foundArticle)
    }

    @Test
    fun findTop100ByWriterUserIdAndUpdatedAtBefore() {
        // given
        val writerUserId = "test@naver.com"
        val body = "본문입니다."
        val imageUrls = listOf("http://aaa.com", "http://bbb.com")
        val article = Article.create(writerUserId, body, imageUrls, ArticleScope.PUBLIC)

        // when
        val writtenArticle1 = articleRepository.save(article)
        val writtenArticle2 = articleRepository.save(article)
        val writtenArticle3 = articleRepository.save(article)

        // then
        val foundArticles = articleRepository.findTop100ByWriterUserIdAndUpdatedAtBefore(writtenArticle3.writerUserId, Instant.now())
        assertNotNull(foundArticles)
        assertTrue(foundArticles.isNotEmpty())
    }

    @Test
    fun findTop100ByWriterUserIdInAndUpdatedAtBeforeAndScopeIn() {
        // given
        val writerUserId = "test2@naver.com"
        val body = "본문입니다."
        val imageUrls = listOf("http://aaa.com", "http://bbb.com")
        val article = Article.create(writerUserId, body, imageUrls, ArticleScope.PUBLIC)

        // when
        val writtenArticle1 = articleRepository.save(article)
        val writtenArticle2 = articleRepository.save(article)
        val writtenArticle3 = articleRepository.save(article)

        // then
        val foundArticles =
            articleRepository.findTop100ByWriterUserIdInAndUpdatedAtBeforeAndScopeIn(
                listOf(writtenArticle3.writerUserId),
                Instant.now(),
                setOf(ArticleScope.FRIEND, ArticleScope.PUBLIC),
            )
        assertNotNull(foundArticles)
        assertTrue(foundArticles.isNotEmpty())
    }
}
