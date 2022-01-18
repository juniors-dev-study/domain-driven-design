package com.sns.article.component.reaction.domains

import java.util.*

interface ReactionRepository {
    fun save(reaction: Reaction): Reaction
    fun findById(id: Long): Optional<Reaction>
    fun deleteById(id: Long)
}
