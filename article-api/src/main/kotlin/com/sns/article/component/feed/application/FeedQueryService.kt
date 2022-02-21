package com.sns.article.component.feed.application

import com.sns.article.component.article.application.ArticleQueryService
import com.sns.article.component.feed.Feed
import com.sns.article.component.reaction.application.ReactionQueryService
import org.springframework.stereotype.Service

/**
 * @author Hyounglin Jun (KR19849)
 */
@Service
class FeedQueryService(
    val articleQueryService: ArticleQueryService,
    // val commentQueryService: CommentQueryService  TODO comment도 연결 필요,
    val reactionQueryService: ReactionQueryService,
) {
    fun getFeeds(loginUserId: String): List<Feed> {
        // TODO 어떻게 노출 할지 고민 필요
        articleQueryService.getArticles(loginUserId).map {  }
        // TODO article의 reaction 조회 필요
        // TODO article의 comments 조회 필요
        // TODO comment의 reaction 조회 필요
        return emptyList()
    }
}
