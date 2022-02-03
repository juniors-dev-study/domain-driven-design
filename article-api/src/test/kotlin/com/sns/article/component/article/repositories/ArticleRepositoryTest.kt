package com.sns.article.component.article.repositories

import com.sns.article.component.article.domains.Article
import org.junit.jupiter.api.Assertions.*
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
        val article = Article.create(writerUserId, body, imageUrls)

        // when
        val writtenArticle = articleRepository.save(article)

        // then
        val foundArticle = articleRepository.findById(writtenArticle.articleId ?: throw Exception()).orElseThrow()
        assertEquals(writtenArticle, foundArticle)
    }

    @Test
    fun findAllByWriterUserId() {
        // given
        val writerUserId = "test@naver.com"
        val body = "본문입니다."
        val imageUrls = listOf("http://aaa.com", "http://bbb.com")
        val article = Article.create(writerUserId, body, imageUrls)

        // when
        val writtenArticle1 = articleRepository.save(article)
        val writtenArticle2 = articleRepository.save(article)
        val writtenArticle3 = articleRepository.save(article)

        // then
        val foundArticles = articleRepository.findAllByWriterUserId(writtenArticle3.writerUserId)
        assertNotNull(foundArticles)
        assertTrue(foundArticles.isNotEmpty())
    }
}
