package com.sns.article.component.comment.application

import com.sns.article.component.comment.domains.Comment
import com.sns.article.component.comment.repositories.CommentCrudRepository
import com.sns.commons.exceptions.NotFoundException
import org.springframework.stereotype.Service

@Service
class CommentCommandService(
    private val commentCrudRepository: CommentCrudRepository
) {
    fun create(type: Comment.Root.Type, rootId: String, contents: String, writerId: String) {
        val newComment = Comment(root = Comment.Root(type, rootId), contents = contents, writerId = writerId)
        commentCrudRepository.save(newComment)
    }

    fun updateContents(id: Long, contents: String, writerId: String) {
        val comment = commentCrudRepository.findById(id).orElseThrow { NotFoundException("댓글이 없습니다.") }
        comment.update(contents, writerId)
        commentCrudRepository.save(comment)
    }

    fun delete(id: Long, writerId: String) {
        val comment = commentCrudRepository.findById(id).orElseThrow { NotFoundException("댓글이 없습니다.") }
        comment.checkAuth(writerId)
        commentCrudRepository.deleteById(id)
    }
}
