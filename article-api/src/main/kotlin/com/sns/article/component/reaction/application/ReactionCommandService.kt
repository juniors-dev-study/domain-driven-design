package com.sns.article.component.reaction.application

import com.sns.article.component.reaction.domains.Reaction
import com.sns.article.component.reaction.domains.ReactionRepository
import com.sns.article.component.reaction.domains.ReactionTarget
import com.sns.article.component.reaction.domains.ReactionValidator
import com.sns.article.component.reaction.dtos.ReactionDto
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
    fun create(dto: ReactionDto): ReactionDto {
        reactionValidator.validate(dto)
        val reaction = reactionRepository.save(
            Reaction(
                target = ReactionTarget(dto.targetType, dto.targetId),
                type = dto.type,
                userId = dto.userId
            )
        )
        reaction.created(applicationEventPublisher)
        reactionRepository.save(reaction)
        return ReactionDto.from(reaction)
    }

    fun delete(id: Long) {
        reactionRepository.deleteById(id)
    }
}
