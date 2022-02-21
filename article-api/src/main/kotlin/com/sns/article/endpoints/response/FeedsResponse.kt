package com.sns.article.endpoints.response

import com.sns.article.component.feed.Feed
import com.sns.article.component.reaction.domains.ReactionType
import java.time.Instant

/**
 * @author Hyounglin Jun
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
    }
    // 생성자를 못쓰는 이유
    // 기본 생성자가 List<FeedResponse>인데, 추가로 List<Feed>를 만들려고 하니까, 2개 동시에 못만듦
    //     constructor(feeds: List<Feed>) : this (
    //         feeds = feeds.map { FeedResponse(it) }
    //     )
}

data class FeedResponse(
    val articleId: Int,
    val imageUrls: List<String>?,
    val body: String?,
    val writerUserId: String,
    val updatedAt: Instant,
    val createdAt: Instant,
    val reactions: List<ReactionResponse>,
    val comments: List<CommentResponse>,
) {
    constructor(feed: Feed) : this(
        articleId = feed.article.articleId,
        imageUrls = feed.article.imageUrls,
        body = feed.article.body,
        writerUserId = feed.article.writerUserId,
        updatedAt = feed.article.updatedAt,
        createdAt = feed.article.createdAt,
        reactions = feed.article.reactions.map { ReactionResponse(it) },
        comments = feed.comments.map { CommentResponse(it) },
    )
}

// TODO 이 Response는 따로 분리예정
data class ReactionResponse(
    var id: Long?,
    val type: ReactionType,
    val owner: Boolean,
) {
    constructor(reaction: Feed.Reaction) : this(
        id = reaction.id,
        type = reaction.type,
        owner = reaction.owner,
    )
}

// TODO 이 Response는 따로 분리예정
data class CommentResponse(
    var id: Long? = null,
    var contents: String,
    val writerId: String,
    var createdAt: Instant,
    var updatedAt: Instant,
    val reaction: Feed.Reaction,
) {
    constructor(comment: Feed.Comment) : this(
        id = comment.id,
        contents = comment.contents,
        writerId = comment.writerId,
        createdAt = comment.createdAt,
        updatedAt = comment.updatedAt,
        reaction = comment.reaction,
    )
}
