package com.sns.article.component.reaction.repositories

import org.springframework.stereotype.Repository


@Repository
class DefaultReactionRepository(reactionCrudRepository: ReactionCrudRepository) : ReactionRepository,
    ReactionCrudRepository by reactionCrudRepository
