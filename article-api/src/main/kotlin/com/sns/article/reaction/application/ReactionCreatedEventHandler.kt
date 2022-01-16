package com.sns.article.reaction.application

import com.sns.article.reaction.domain.ReactionCreatedEvent
import com.sns.article.reaction.domain.ReactionNotFoundException
import com.sns.article.reaction.domain.ReactionRepository
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class ReactionCreatedEventHandler(private val reactionRepository: ReactionRepository) {
    @EventListener
    fun handle(event: ReactionCreatedEvent) {
        val id = event.reactionId
        val reaction = reactionRepository.findById(id).orElseThrow { ReactionNotFoundException() }

        // TODO : 반응 생성 알림 생성 (게시글 또는 댓글)
    }
}
