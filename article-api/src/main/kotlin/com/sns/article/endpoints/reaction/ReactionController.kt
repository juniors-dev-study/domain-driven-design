package com.sns.article.endpoints.reaction

import com.sns.article.component.reaction.application.ReactionCommandService
import com.sns.article.component.reaction.application.ReactionQueryService
import com.sns.article.component.reaction.dtos.ReactionDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

// TODO : 권한 체크
@RestController
@RequestMapping("/api/v1/reactions")
class ReactionController(
    private val reactionCommandService: ReactionCommandService,
    private val reactionQueryService: ReactionQueryService
) {
    @PostMapping
    fun createReaction(@RequestBody reactionDto: ReactionDto): ResponseEntity<Void> {
        val reaction = reactionCommandService.create(reactionDto)
        return ResponseEntity.created(URI.create("/api/v1/reactions/" + reaction.id)).build()
    }

    @GetMapping("/{reactionId}")
    fun findReaction(@PathVariable reactionId: Long): ResponseEntity<ReactionDto> {
        val reaction = reactionQueryService.findById(reactionId)
        return ResponseEntity.ok(reaction)
    }

    @DeleteMapping("/{reactionId}")
    fun deleteReaction(@PathVariable reactionId: Long): ResponseEntity<Void> {
        reactionCommandService.delete(reactionId)
        return ResponseEntity.noContent().build()
    }
}
