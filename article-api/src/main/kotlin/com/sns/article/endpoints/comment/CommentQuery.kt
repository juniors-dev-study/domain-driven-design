package com.sns.article.endpoints.comment

import com.sns.article.component.comment.domains.Comment

interface CommentQuery {
    fun getByRoot(root: Comment.Root): List<Comment>
    fun getByWriterId(writerId: String): List<Comment>
}
