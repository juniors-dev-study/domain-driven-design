package com.sns.article.reaction.dto

import com.sns.article.reaction.domain.Reaction
import com.sns.article.reaction.domain.ReactionTargetType
import com.sns.article.reaction.domain.ReactionType

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
