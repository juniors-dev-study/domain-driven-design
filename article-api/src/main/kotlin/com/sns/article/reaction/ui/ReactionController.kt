package com.sns.article.reaction.ui

import com.sns.article.reaction.application.ReactionService
import com.sns.article.reaction.dto.ReactionDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

// TODO : 권한 체크
@RestController
@RequestMapping("/api/v1/reactions")
class ReactionController(private val reactionService: ReactionService) {
    @PostMapping
    fun createReaction(@RequestBody reactionDto: ReactionDto): ResponseEntity<Void> {
        val reaction = reactionService.create(reactionDto)
        return ResponseEntity.created(URI.create("/api/v1/reactions/" + reaction.id)).build()
    }

    @GetMapping("/{reactionId}")
    fun findReaction(@PathVariable reactionId: Long): ResponseEntity<ReactionDto> {
        val reaction = reactionService.findById(reactionId)
        return ResponseEntity.ok(reaction)
    }

    @DeleteMapping("/{reactionId}")
    fun deleteReaction(@PathVariable reactionId: Long): ResponseEntity<Void> {
        reactionService.delete(reactionId)
        return ResponseEntity.noContent().build()
    }
}
