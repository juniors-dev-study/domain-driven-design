package com.sns.article.component.reaction.domains

import com.sns.commons.exceptions.NoAuthorityException
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Embedded
import org.springframework.data.relational.core.mapping.Table

@Table(value = "reaction")
class Reaction(
    @field:Id @field:Column(value = "id") var id: Long? = null,
    @field:Embedded(onEmpty = Embedded.OnEmpty.USE_EMPTY) val target: ReactionTarget,
    @field:Column(value = "type") val type: ReactionType,
    @field:Column(value = "user_id") val userId: String
) {
    fun created(eventPublisher: ApplicationEventPublisher?) {
        eventPublisher?.publishEvent(ReactionCreatedEvent(id!!))
    }

    fun deleteBy(userId: String) {
        if (userId != userId) {
            throw NoAuthorityException()
        }
    }
}
