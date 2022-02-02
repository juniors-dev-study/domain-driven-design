package com.sns.article.endpoints.reaction.requests

import com.sns.article.component.reaction.domains.ReactionTargetType
import com.sns.article.component.reaction.domains.ReactionType
import kotlinx.serialization.Serializable
import javax.validation.constraints.NotNull

@Serializable
data class ReactionCreateRequest(
    @NotNull
    val targetType: ReactionTargetType,
    @NotNull
    val targetId: Long,
    @NotNull
    val type: ReactionType,
)
