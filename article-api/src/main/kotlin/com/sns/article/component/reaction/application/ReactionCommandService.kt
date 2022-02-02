package com.sns.article.component.reaction.application

import com.sns.article.component.reaction.domains.Reaction
import com.sns.article.component.reaction.domains.ReactionRepository
import com.sns.article.component.reaction.domains.ReactionTarget
import com.sns.article.component.reaction.domains.ReactionValidator
import com.sns.article.component.reaction.dtos.ReactionDto
import com.sns.article.endpoints.reaction.requests.ReactionCreateRequest
import com.sns.commons.exceptions.NotFoundException
import com.sns.commons.oauth.LoginUser
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
    fun create(request: ReactionCreateRequest, loginUser: LoginUser): ReactionDto {
        reactionValidator.validate(request, loginUser)
        val reaction = reactionRepository.save(
            Reaction(
                target = ReactionTarget(request.targetType, request.targetId),
                type = request.type,
                userId = loginUser.id,
            ),
        )
        reaction.created(applicationEventPublisher)
        reactionRepository.save(reaction)
        return ReactionDto.from(reaction)
    }

    fun delete(id: Long, loginUser: LoginUser) {
        val reaction = reactionRepository.findById(id).orElseThrow { NotFoundException(id.toString()) }
        reaction.deleteBy(loginUser)
        reactionRepository.deleteById(id)
    }
}
