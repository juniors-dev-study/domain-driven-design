package com.sns.article.endpoints.response

import com.sns.article.component.article.domains.Article
import com.sns.article.component.article.domains.ArticleId
import com.sns.article.component.feed.Feed
import com.sns.article.component.reaction.domains.Reaction
import com.sns.article.component.reaction.domains.ReactionTarget
import com.sns.article.component.reaction.domains.ReactionType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Embedded
import java.time.Instant

/**
 * @author Hyounglin Jun (KR19849)
 */
class FeedsResponse(
    val feeds: List<FeedResponse>,
) {
    companion object {
        fun create(
            list: List<Feed>,
        ): FeedsResponse {
            return FeedsResponse(
                list.map { FeedResponse(it) }.toList(),
            )
        }

        // TODO 삭제 필요 Mocking test용
        fun createMock(): FeedsResponse {
            return FeedsResponse(
                listOf(
                    FeedResponse(
                        articleId = ArticleId(10000),
                        imageUrls = listOf("https://picsum.photos/seed/picsum/200/300").toMutableList(),
                        body = "본문 내용입니다",
                        writerUserId = "bearics",
                        createdAt = Instant.now(),
                        updatedAt = Instant.now(),
                        reaction = ReactionResponse(2000L, ReactionType.LIKE, "test_01"),
                        comments = listOf(
                            CommentResponse(3000L, "댓글입니다.", "test_02", Instant.now(), Instant.now()),
                            CommentResponse(3000L, "댓글입니다.", "test_02", Instant.now(), Instant.now()),
                        ),
                    ),
                    FeedResponse(
                        articleId = ArticleId(10000),
                        imageUrls = listOf("https://picsum.photos/seed/picsum/200/300").toMutableList(),
                        body = "본문 내용입니다",
                        writerUserId = "bearics",
                        createdAt = Instant.now(),
                        updatedAt = Instant.now(),
                        reaction = ReactionResponse(2000L, ReactionType.LIKE, "test_01"),
                        comments = listOf(
                            CommentResponse(3000L, "댓글입니다.", "test_02", Instant.now(), Instant.now()),
                            CommentResponse(3000L, "댓글입니다.", "test_02", Instant.now(), Instant.now()),
                        ),
                    )
                )
            )
        }
    }
    // 생성자를 못쓰는 이유
    // 기본 생성자가 List<FeedResponse>인데, 추가로 List<Feed>를 만들려고 하니까, 2개 동시에 못만듦
    //     constructor(feeds: List<Feed>) : this (
    //         feeds = feeds.map { FeedResponse(it) }
    //     )
}

data class FeedResponse(
    val articleId: ArticleId?,
    val imageUrls: MutableList<String>?,
    val body: String?,
    val writerUserId: String,
    val updatedAt: Instant,
    val createdAt: Instant,
    val reaction: ReactionResponse,
    val comments: List<CommentResponse>,
) {
    constructor(feed: Feed) : this(
        articleId = feed.article.articleId,
        imageUrls = feed.article.imageUrls,
        body = feed.article.body,
        writerUserId = feed.article.writerUserId,
        updatedAt = feed.article.updatedAt,
        createdAt = feed.article.createdAt,
        reaction = ReactionResponse(feed.article.reaction),
        comments = feed.comments.map { CommentResponse(it) },
    )
}

// TODO 이 Response는 따로 분리예정
data class ReactionResponse(
    var id: Long?,
    val type: ReactionType,
    val userId: String,
) {
    constructor(reaction: Feed.Reaction) : this(
        id = reaction.id,
        type = reaction.type,
        userId = reaction.userId,
    )
}

// TODO 이 Response는 따로 분리예정
data class CommentResponse(
    var id: Long? = null,
    var contents: String,
    val writerId: String,
    var createdAt: Instant,
    var updatedAt: Instant,
) {
    constructor(comment: Feed.Comment) : this(
        id = comment.id,
        contents = comment.contents,
        writerId = comment.writerId,
        createdAt = comment.createdAt,
        updatedAt = comment.updatedAt,
    )
}
