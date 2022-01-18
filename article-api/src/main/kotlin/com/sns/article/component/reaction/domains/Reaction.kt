package com.sns.article.component.reaction.domains

import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Embedded

class Reaction(
    @field:Id var id: Long? = null,
    @field:Embedded(onEmpty = Embedded.OnEmpty.USE_NULL) val target: ReactionTarget,
    val type: ReactionType,
    val userId: String
) {
    fun created(eventPublisher: ApplicationEventPublisher?) {
        eventPublisher?.publishEvent(ReactionCreatedEvent(id!!))
    }
}
