package com.sns.article.reaction.domain;

public class ReactionTarget {
	private final ReactionTargetType type;
	private final Long id;

	public ReactionTarget(ReactionTargetType type, Long id) {
		this.type = type;
		this.id = id;
	}

	public ReactionTargetType getType() {
		return type;
	}

	public Long getId() {
		return id;
	}
}
