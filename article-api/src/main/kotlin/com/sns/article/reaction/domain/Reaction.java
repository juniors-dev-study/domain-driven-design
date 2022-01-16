package com.sns.article.reaction.domain;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Embedded;

public class Reaction {
	@Id
	private final Long id;

	@Embedded(onEmpty = Embedded.OnEmpty.USE_NULL)
	private final ReactionTarget target;

	private final ReactionType type;

	private final String userId;

	public Reaction(Long id, ReactionTarget target, ReactionType type, String userId) {
		this.id = id;
		this.target = target;
		this.type = type;
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public ReactionTarget getTarget() {
		return target;
	}

	public ReactionType getType() {
		return type;
	}

	public String getUserId() {
		return userId;
	}

	public void created(ApplicationEventPublisher eventPublisher) {
		if (eventPublisher != null) {
			eventPublisher.publishEvent(new ReactionCreatedEvent(id));
		}
	}
}
