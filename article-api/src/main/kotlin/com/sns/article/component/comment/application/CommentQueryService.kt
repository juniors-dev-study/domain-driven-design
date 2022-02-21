package com.sns.article.component.comment.application

import com.sns.article.component.comment.domains.Comment
import com.sns.article.component.comment.repositories.CommentRepository
import com.sns.article.endpoints.comment.CommentEndPointQuery
import org.springframework.stereotype.Service

@Service
class CommentQueryService(val commentRepository: CommentRepository) : CommentEndPointQuery {
    fun Comment.Root.isInvalid(): Boolean = this.id.isNotBlank()

    override fun getByRoot(root: Comment.Root): List<Comment> {
        if (root.isInvalid()) return listOf()
        return commentRepository.findAllByRootIdInAndRootType(listOf(root.id), root.type)
    }

    override fun getByWriterId(writerId: String): List<Comment> {
        if (writerId.isBlank()) return listOf()
        return commentRepository.findAllByWriterId(writerId)
    }
}
