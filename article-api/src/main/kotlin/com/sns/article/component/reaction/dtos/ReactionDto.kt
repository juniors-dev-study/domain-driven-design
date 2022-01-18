package com.sns.article.component.reaction.dtos

import com.sns.article.component.reaction.domains.Reaction
import com.sns.article.component.reaction.domains.ReactionTargetType
import com.sns.article.component.reaction.domains.ReactionType

class ReactionDto(
    val id: Long,
    val targetType: ReactionTargetType,
    val targetId: Long,
    val type: ReactionType,
    val userId: String
) {

    companion object {
        fun from(reaction: Reaction): ReactionDto {
            return ReactionDto(
                reaction.id!!,
                reaction.target.type,
                reaction.target.id,
                reaction.type,
                reaction.userId
            )
        }
    }
}
