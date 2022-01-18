package com.sns.article.component.reaction.domains

import org.springframework.data.relational.core.mapping.Column

data class ReactionTarget(
    @field:Column(value = "target_type") val type: ReactionTargetType,
    @field:Column(value = "target_id") val id: Long
)
