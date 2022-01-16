package com.sns.article.reaction.domain;

public class ReactionCreatedEvent {
	private final Long reactionId;

	public ReactionCreatedEvent(Long reactionId) {
		this.reactionId = reactionId;
	}

	public Long getReactionId() {
		return reactionId;
	}
}
