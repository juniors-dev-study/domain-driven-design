package com.sns.article.component.reaction.domains

import com.sns.article.endpoints.reaction.requests.ReactionCreateRequest
import com.sns.commons.oauth.LoginUser
import org.springframework.stereotype.Component

@Component
class ReactionValidator {
    fun validate(request: ReactionCreateRequest, loginUser: LoginUser) {
        // TODO : 좋아요 대상(게시글 또는 댓글)이 존재하지 않는 경우 또는 사용자가 존재하지 않는 경우 예외를 던진다.
    }
}
