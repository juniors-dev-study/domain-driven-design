package com.sns.article.reaction.domain

import com.sns.article.reaction.dto.ReactionDto
import org.springframework.stereotype.Component

@Component
class ReactionValidator {
    fun validate(dto: ReactionDto) {
        // TODO : 좋아요 대상(게시글 또는 댓글)이 존재하지 않는 경우 또는 사용자가 존재하지 않는 경우 예외를 던진다.
    }
}
