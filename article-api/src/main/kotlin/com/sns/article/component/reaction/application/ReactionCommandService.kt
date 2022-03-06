package com.sns.article.component.reaction.application

import com.sns.article.component.reaction.domains.Reaction
import com.sns.article.component.reaction.domains.ReactionTarget
import com.sns.article.component.reaction.domains.ReactionValidator
import com.sns.article.component.reaction.dtos.ReactionDto
import com.sns.article.component.reaction.repositories.ReactionRepository
import com.sns.article.endpoints.reaction.requests.ReactionCreateRequest
import com.sns.commons.exceptions.NotFoundException
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ReactionCommandService(
    private val reactionRepository: ReactionRepository,
    private val reactionValidator: ReactionValidator,
    private val applicationEventPublisher: ApplicationEventPublisher
) {
    fun create(request: ReactionCreateRequest, userId: String): ReactionDto {
        reactionValidator.validate(request, userId)
        val reaction = reactionRepository.save(
            Reaction(
                target = ReactionTarget(request.targetType, request.targetId),
                type = request.type,
                userId = userId,
            ),
        )
        reaction.created(applicationEventPublisher)
        reactionRepository.save(reaction)
        return ReactionDto.from(reaction)
    }

    fun delete(id: Long, userId: String) {
        val reaction = reactionRepository.findById(id).orElseThrow { NotFoundException(id.toString()) }
        reaction.deleteBy(userId)
        reactionRepository.deleteById(id)
    }
}
