package com.sns.article.component.comment.repositories

import com.sns.article.component.comment.domains.Comment
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentCrudRepository : CrudRepository<Comment, Long> {
}
