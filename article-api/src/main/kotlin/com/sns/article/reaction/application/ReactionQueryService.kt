package com.sns.article.reaction.application

import com.sns.article.reaction.domain.ReactionNotFoundException
import com.sns.article.reaction.domain.ReactionRepository
import com.sns.article.reaction.dto.ReactionDto
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
