package com.sns.article.endpoints.comment

import com.sns.article.component.comment.domains.Comment

interface CommentEndPointCommand {
    fun create(type: Comment.Root.Type, rootId: String, contents: String, writerId: String) : Comment
    fun updateContents(id: Long, contents: String, writerId: String) : Comment
    fun delete(id: Long, writerId: String)
}
