package com.sns.article.component.reaction.repositories

import com.sns.article.component.reaction.domains.Reaction
import com.sns.article.component.reaction.domains.ReactionRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SpringDataJdbcReactionRepository : ReactionRepository, CrudRepository<Reaction, Long>
