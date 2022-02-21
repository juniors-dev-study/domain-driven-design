package com.sns.article.component.comment.repositories

import com.sns.article.component.comment.domains.Comment
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : CrudRepository<Comment, Long> {
    fun findAllByRootIdInAndRootType(rootIds: List<String>, rootType: Comment.Root.Type): List<Comment>

    fun findAllByWriterId(writerId: String): List<Comment>
}
