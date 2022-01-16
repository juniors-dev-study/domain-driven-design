package com.sns.article.reaction.application;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sns.article.reaction.domain.Reaction;
import com.sns.article.reaction.domain.ReactionCreatedEvent;
import com.sns.article.reaction.domain.ReactionNotFoundException;
import com.sns.article.reaction.domain.ReactionRepository;

@Component
@Transactional
public class ReactionCreatedEventHandler {
	private final ReactionRepository reactionRepository;

	public ReactionCreatedEventHandler(ReactionRepository reactionRepository) {
		this.reactionRepository = reactionRepository;
	}

	@EventListener
	public void handle(ReactionCreatedEvent event) {
		Long id = event.getReactionId();
		Reaction reaction = reactionRepository.findById(id).orElseThrow(ReactionNotFoundException::new);

		// TODO : 반응 생성 알림 생성 (게시글 또는 댓글)
	}
}
