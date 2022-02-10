package com.sns.article.endpoints.comment.requests

import com.sns.article.component.comment.domains.Comment
import kotlinx.serialization.Serializable
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Serializable
data class CommentCreateRequest(
    @NotNull
    val rootType: Comment.Root.Type,
    @NotNull
    val rootId: String,
    @NotEmpty
    val contents: String
)
