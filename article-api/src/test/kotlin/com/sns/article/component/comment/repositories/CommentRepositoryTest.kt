package com.sns.article.component.comment.repositories

import com.sns.article.component.comment.domains.Comment
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CommentRepositoryTest() {
    @Autowired
    lateinit var commentRepository: CommentRepository

    @BeforeEach
    internal fun insert() {
        commentRepository.save(Comment.create(Comment.Root(Comment.Root.Type.ARTICLE, "000111"), contents = "댓글 내용", writerId = "writerId"))
    }

    @Test
    internal fun findById() {
        assertThat(commentRepository.findById(1)).isNotNull
    }

    @Test
    internal fun findAllByWriterId() {
        assertThat(commentRepository.findAllByWriterId("writerId")).isNotEmpty
    }

    @Test
    internal fun findAllByRootIdInAndRootType() {
        assertThat(commentRepository.findAllByRootIdInAndRootType(rootIds = listOf("000111"), rootType = Comment.Root.Type.ARTICLE)).isNotEmpty
    }
}
