package com.sns.article.component.feed.application

import com.sns.article.component.article.application.ArticleQueryService
import com.sns.article.component.feed.Feed
import com.sns.article.component.reaction.application.ReactionQueryService
import java.time.Instant
import org.springframework.stereotype.Service

/**
 * @author Hyounglin Jun
 */
@Service
class FeedQueryService(
    val articleQueryService: ArticleQueryService,
    // val commentQueryService: CommentQueryService  TODO comment도 연결 필요,
    val reactionQueryService: ReactionQueryService,
) {
    fun getFeeds(loginUserId: String, lastUpdatedAt: Instant, count: Int): List<Feed> {
        // TODO 어떻게 노출 할지 고민 필요
        val userArticles = articleQueryService.getArticles(loginUserId, lastUpdatedAt).take(count)

        // TODO 친구 목록 API 가져와서 조회
        val friendsArticles = articleQueryService.getFriendsArticles(listOf(), lastUpdatedAt).take(count)

        val articles = (userArticles + friendsArticles).asSequence()
            .sortedByDescending { it.updatedAt }
            .take(count)
            .toList()

        // TODO article의 reaction 조회 필요
        // TODO article의 comments 조회 필요
        // TODO comment의 reaction 조회 필요
        return emptyList()
    }
}
