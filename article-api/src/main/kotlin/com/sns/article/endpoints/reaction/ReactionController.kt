package com.sns.article.endpoints.reaction

import com.sns.article.component.reaction.application.ReactionCommandService
import com.sns.article.component.reaction.application.ReactionQueryService
import com.sns.article.component.reaction.dtos.ReactionDto
import com.sns.article.endpoints.reaction.requests.ReactionCreateRequest
import com.sns.commons.annotation.IsLoginUser
import com.sns.commons.oauth.LoginUser
import java.net.URI
import javax.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/reactions")
class ReactionController(
    private val reactionCommandService: ReactionCommandService,
    private val reactionQueryService: ReactionQueryService
) {
    @IsLoginUser
    @PostMapping
    fun createReaction(@Valid @RequestBody request: ReactionCreateRequest, loginUser: LoginUser): ResponseEntity<Void> {
        val reaction = reactionCommandService.create(request, loginUser)
        return ResponseEntity.created(URI.create("/api/v1/reactions/" + reaction.id)).build()
    }

    @GetMapping("/{reactionId}")
    fun findReaction(@PathVariable reactionId: Long): ResponseEntity<ReactionDto> {
        val reaction = reactionQueryService.findById(reactionId)
        return ResponseEntity.ok(reaction)
    }

    @IsLoginUser
    @DeleteMapping("/{reactionId}")
    fun deleteReaction(@PathVariable reactionId: Long, loginUser: LoginUser): ResponseEntity<Void> {
        reactionCommandService.delete(reactionId, loginUser)
        return ResponseEntity.noContent().build()
    }
}
