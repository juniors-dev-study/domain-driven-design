package com.sns.article.reaction.dto;

import com.sns.article.reaction.domain.Reaction;
import com.sns.article.reaction.domain.ReactionTargetType;
import com.sns.article.reaction.domain.ReactionType;

public class ReactionDto {
	private final Long id;
	private final ReactionTargetType targetType;
	private final Long targetId;
	private final ReactionType type;
	private final String userId;

	public ReactionDto(Long id, ReactionTargetType targetType, Long targetId, ReactionType type, String userId) {
		this.id = id;
		this.targetType = targetType;
		this.targetId = targetId;
		this.type = type;
		this.userId = userId;
	}

	public static ReactionDto from(Reaction reaction) {
		return new ReactionDto(reaction.getId(),
			reaction.getTarget().getType(),
			reaction.getTarget().getId(),
			reaction.getType(),
			reaction.getUserId());
	}

	public Long getId() {
		return id;
	}

	public ReactionTargetType getTargetType() {
		return targetType;
	}

	public Long getTargetId() {
		return targetId;
	}

	public ReactionType getType() {
		return type;
	}

	public String getUserId() {
		return userId;
	}
}
