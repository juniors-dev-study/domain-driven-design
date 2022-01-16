package com.sns.article.reaction.application;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sns.article.reaction.domain.Reaction;
import com.sns.article.reaction.domain.ReactionNotFoundException;
import com.sns.article.reaction.domain.ReactionRepository;
import com.sns.article.reaction.domain.ReactionTarget;
import com.sns.article.reaction.domain.ReactionValidator;
import com.sns.article.reaction.dto.ReactionDto;

@Service
@Transactional(readOnly = true)
public class ReactionService {
	private final ReactionRepository reactionRepository;
	private final ReactionValidator reactionValidator;
	private final ApplicationEventPublisher applicationEventPublisher;

	public ReactionService(
		ReactionRepository reactionRepository,
		ReactionValidator reactionValidator,
		ApplicationEventPublisher applicationEventPublisher
	) {
		this.reactionRepository = reactionRepository;
		this.reactionValidator = reactionValidator;
		this.applicationEventPublisher = applicationEventPublisher;
	}

	@Transactional
	public ReactionDto create(ReactionDto dto) {
		reactionValidator.validate(dto);
		ReactionTarget target = new ReactionTarget(dto.getTargetType(), dto.getTargetId());
		Reaction reaction = reactionRepository.save(new Reaction(dto.getId(), target, dto.getType(), dto.getUserId()));
		reaction.created(applicationEventPublisher);
		reactionRepository.save(reaction);
		return ReactionDto.from(reaction);
	}

	public ReactionDto findById(Long id) {
		Reaction reaction = reactionRepository.findById(id).orElseThrow(ReactionNotFoundException::new);
		return ReactionDto.from(reaction);
	}

	@Transactional
	public void delete(Long id) {
		reactionRepository.deleteById(id);
	}
}
