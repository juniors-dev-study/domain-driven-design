package com.sns.article.component.comment.application

import com.sns.article.component.comment.domains.Comment
import com.sns.article.component.comment.repositories.CommentCrudRepository
import com.sns.article.endpoints.comment.CommentQuery
import com.sns.commons.utils.log
import org.springframework.stereotype.Service

@Service
class CommentQueryService(val commentCrudRepository: CommentCrudRepository) : CommentQuery {
    val log = this.log()

    override fun getByRoot(root : Comment.Root) : List<Comment> {
        return listOf()
    }

    override fun getByWriterId(writerId : String) : List<Comment>{
        return listOf()
    }
}
