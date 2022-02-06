package com.sns.article.component.comment.repositories

import com.sns.article.component.comment.domains.Comment
import com.sns.article.component.comment.domains.RootType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class CommentRepositoryTest {
    @Autowired
    lateinit var commentRepository: CommentRepository

    @Test
    fun create() {
        assertThat(
            commentRepository.save(
                Comment.create(RootType.ARTICLE, "2", "내용", "작성자"),
            ),
        ).isNotNull
    }

    @Test
    fun findAllByRootIdInAndRootType() {
        assertThat(
            commentRepository.findAllByRootIdInAndRootType(listOf("1"), RootType.ARTICLE),
        ).isNotEmpty
    }
}
