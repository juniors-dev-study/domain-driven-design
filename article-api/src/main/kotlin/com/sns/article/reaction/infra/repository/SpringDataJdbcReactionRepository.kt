package com.sns.article.reaction.infra.repository

import com.sns.article.reaction.domain.Reaction
import com.sns.article.reaction.domain.ReactionRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SpringDataJdbcReactionRepository : ReactionRepository, CrudRepository<Reaction, Long>
