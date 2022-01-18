package com.sns.article.component.reaction.application

import com.sns.article.component.reaction.domains.ReactionNotFoundException
import com.sns.article.component.reaction.domains.ReactionRepository
import com.sns.article.component.reaction.dtos.ReactionDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ReactionQueryService(private val reactionRepository: ReactionRepository) {

    fun findById(id: Long): ReactionDto {
        val reaction = reactionRepository.findById(id).orElseThrow { ReactionNotFoundException() }
        return ReactionDto.from(reaction)
    }
}
