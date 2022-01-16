package com.sns.article.reaction.ui;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sns.article.reaction.application.ReactionService;
import com.sns.article.reaction.dto.ReactionDto;

// TODO : 권한 체크
@RestController
@RequestMapping("/api/v1/reactions")
public class ReactionController {
	private final ReactionService reactionService;

	public ReactionController(ReactionService reactionService) {
		this.reactionService = reactionService;
	}

	@PostMapping
	public ResponseEntity<Void> createReaction(@RequestBody ReactionDto reactionDto) {
		ReactionDto reaction = reactionService.create(reactionDto);
		return ResponseEntity.created(URI.create("/api/v1/reactions" + reaction.getId())).build();
	}

	@GetMapping("/{reactionId}")
	public ResponseEntity<ReactionDto> findReaction(@PathVariable Long reactionId) {
		ReactionDto reaction = reactionService.findById(reactionId);
		return ResponseEntity.ok(reaction);
	}

	@DeleteMapping("/{reactionId}")
	public ResponseEntity<Void> deleteReaction(@PathVariable Long reactionId) {
		reactionService.delete(reactionId);
		return ResponseEntity.noContent().build();
	}
}
