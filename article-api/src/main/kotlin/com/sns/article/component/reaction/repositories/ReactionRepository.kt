package com.sns.article.component.reaction.repositories

import com.sns.article.component.reaction.domains.Reaction
import com.sns.article.component.reaction.domains.ReactionTarget
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * Created by JunSeok Youn on 2022/03/06
 */
@Repository
interface ReactionRepository : CrudRepository<Reaction, Long> {
    fun findAllByTargetIn(targets: List<ReactionTarget>): List<Reaction>
}
