package com.sns.article.reaction.domain

import org.springframework.data.relational.core.mapping.Column

data class ReactionTarget(
        @field:Column(value = "target_type") val type: ReactionTargetType,
        @field:Column(value = "target_id") val id: Long
)
