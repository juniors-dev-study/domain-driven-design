package com.sns.article.reaction.domain

import java.util.*

interface ReactionRepository {
    fun save(reaction: Reaction): Reaction
    fun findById(id: Long): Optional<Reaction>
    fun deleteById(id: Long)
}
