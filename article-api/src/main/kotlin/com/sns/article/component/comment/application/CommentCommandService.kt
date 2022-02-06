package com.sns.article.component.comment.application

import com.sns.article.component.comment.domains.Comment
import com.sns.article.component.comment.domains.RootType
import com.sns.article.component.comment.repositories.CommentRepository
import com.sns.commons.exceptions.NotFoundException
import org.springframework.stereotype.Service

@Service
class CommentCommandService(
    private val commentRepository: CommentRepository
) {
    fun create(rootType: RootType, rootId: String, contents: String, writerId: String): Comment {
        val newComment = Comment(rootType = rootType, rootId = rootId, contents = contents, writerId = writerId)
        return commentRepository.save(newComment)
    }

    fun updateContents(id: Long, contents: String, writerId: String) {
        val comment = commentRepository.findById(id).orElseThrow { NotFoundException("댓글이 없습니다.") }
        comment.update(contents, writerId)
        commentRepository.save(comment)
    }

    fun delete(id: Long, writerId: String) {
        val comment = commentRepository.findById(id).orElseThrow { NotFoundException("댓글이 없습니다.") }
        comment.checkAuth(writerId)
        commentRepository.deleteById(id)
    }
}
