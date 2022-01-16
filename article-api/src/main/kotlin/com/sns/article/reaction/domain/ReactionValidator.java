package com.sns.article.reaction.domain;

import org.springframework.stereotype.Component;

import com.sns.article.reaction.dto.ReactionDto;

@Component
public class ReactionValidator {

	public void validate(ReactionDto dto) {
		// TODO : 좋아요 대상(게시글 또는 댓글)이 존재하지 않는 경우 예외를 던진다.
	}
}
