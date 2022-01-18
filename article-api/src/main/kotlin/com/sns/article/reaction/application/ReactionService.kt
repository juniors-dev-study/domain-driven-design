package com.sns.article.reaction.application

import com.sns.article.reaction.domain.*
import com.sns.article.reaction.dto.ReactionDto
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ReactionService(
    private val reactionRepository: ReactionRepository,
    private val reactionValidator: ReactionValidator,
    private val applicationEventPublisher: ApplicationEventPublisher
) {
    @Transactional
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

    fun findById(id: Long): ReactionDto {
        val reaction = reactionRepository.findById(id).orElseThrow { ReactionNotFoundException() }
        return ReactionDto.from(reaction)
    }

    @Transactional
    fun delete(id: Long) {
        reactionRepository.deleteById(id)
    }
}
