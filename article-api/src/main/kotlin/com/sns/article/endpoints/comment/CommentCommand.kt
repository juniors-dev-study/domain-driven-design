package com.sns.article.endpoints.comment

import com.sns.article.component.comment.domains.Comment

interface CommentCommand {
    fun create(type: Comment.Root.Type, rootId: String, contents: String, writerId: String)
    fun updateContents(id: Long, contents: String, writerId: String)
    fun delete(id: Long, writerId: String)
}
