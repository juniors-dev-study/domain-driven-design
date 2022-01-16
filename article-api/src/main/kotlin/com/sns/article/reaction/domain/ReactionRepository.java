package com.sns.article.reaction.domain;

import java.util.Optional;

public interface ReactionRepository {
	Reaction save(Reaction reaction);

	Optional<Reaction> findById(Long id);

	void deleteById(Long id);
}
