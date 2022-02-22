package com.sns.article.component.comment.application

import com.sns.article.component.comment.domains.Comment
import com.sns.article.component.comment.repositories.CommentRepository
import com.sns.article.endpoints.comment.CommentEndPointCommand
import com.sns.commons.exceptions.NotFoundException
import org.springframework.stereotype.Service

@Service
class CommentCommandService(
    private val commentRepository: CommentRepository
) : CommentEndPointCommand {
    override fun create(type: Comment.Root.Type, rootId: String, contents: String, writerId: String): Comment {
        val newComment = Comment(root = Comment.Root(type, rootId), contents = contents, writerId = writerId)
        return commentRepository.save(newComment)
    }

    override fun updateContents(id: Long, contents: String, writerId: String): Comment {
        val comment = commentRepository.findById(id).orElseThrow { NotFoundException("댓글이 없습니다.") }
        comment.update(contents, writerId)
        return commentRepository.save(comment)
    }

    override fun delete(id: Long, writerId: String) {
        val comment = commentRepository.findById(id).orElseThrow { NotFoundException("댓글이 없습니다.") }
        comment.checkAuth(writerId)
        commentRepository.deleteById(id)
    }
}
