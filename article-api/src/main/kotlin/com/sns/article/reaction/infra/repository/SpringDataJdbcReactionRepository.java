package com.sns.article.reaction.infra.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sns.article.reaction.domain.Reaction;
import com.sns.article.reaction.domain.ReactionRepository;

@Repository
public interface SpringDataJdbcReactionRepository extends ReactionRepository, CrudRepository<Reaction, Long> {
}
