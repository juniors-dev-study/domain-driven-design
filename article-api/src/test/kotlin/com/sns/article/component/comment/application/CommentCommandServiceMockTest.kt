package com.sns.article.component.comment.application

import com.sns.article.component.comment.domains.Comment
import com.sns.article.component.comment.domains.RootType
import com.sns.article.component.comment.repositories.CommentCrudRepository
import com.sns.commons.exceptions.NoAuthorityException
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import java.time.Instant
import java.util.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class CommentCommandServiceMockTest {
    @MockK
    private lateinit var commentCrudRepository: CommentCrudRepository

    @InjectMockKs
    private lateinit var commentCommandService: CommentCommandService

    private val rootId = 1

    private val comment = Comment(1, RootType.ARTICLE, rootId.toString(), "기존 내용", "writerId", Instant.now(), Instant.now())

    @BeforeEach
    internal fun setUp() {
        MockKAnnotations.init(this, relaxed = true)

        every { commentCrudRepository.save(any()) } returnsArgument 0
        every { commentCrudRepository.deleteById(any()) } returns Unit
        every { commentCrudRepository.findById(1) } returns Optional.of(comment)
    }

    @Test
    fun create() {
        val rootId = 999
        commentCommandService.create(RootType.ARTICLE, rootId.toString(), "새로운 댓글", "writerId")
        verify { commentCrudRepository.save(ofType(Comment::class)) }
    }

    @DisplayName("동일한 writer가 댓글을 수정할 경우, 성공해야한다.")
    @Test
    fun update_success() {
        commentCommandService.update(comment.id ?: 0, "내용 업데이트", comment.writerId)

        verify { commentCrudRepository.save(ofType(Comment::class)) }
    }

    @DisplayName("다른 writer가 댓글을 수정할경우, 실패해야한다.")
    @Test
    fun update_fail() {
        assertThrows<NoAuthorityException>(message = "권한체크에 실패해야한다.") {
            commentCommandService.update(comment.id ?: 0, "내용 업데이트", "different_writer")
        }
    }

    @DisplayName("동일한 writer가 댓글을 삭제할 경우, 성공해야한다.")
    @Test
    fun delete_success() {
        commentCommandService.delete(comment.id ?: 0, comment.writerId)

        verify { commentCrudRepository.deleteById(comment.id ?: 0) }
    }

    @DisplayName("다른 writer가 댓글을 삭제할 경우, 실패해야한다.")
    @Test
    fun delete_fail() {
        assertThrows<NoAuthorityException>(message = "권한체크에 실패해야한다.") {
            commentCommandService.delete(comment.id ?: 0, "different_writer")
        }
    }
}
