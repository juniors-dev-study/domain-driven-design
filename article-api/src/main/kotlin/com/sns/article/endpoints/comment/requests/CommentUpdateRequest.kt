package com.sns.article.endpoints.comment.requests

import javax.validation.constraints.NotEmpty

data class CommentUpdateRequest(
    @NotEmpty
    val contents: String
)
