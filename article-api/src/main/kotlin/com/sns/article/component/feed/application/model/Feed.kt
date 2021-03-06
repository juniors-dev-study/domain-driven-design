package com.sns.article.component.feed

import com.sns.article.component.reaction.domains.ReactionType
import java.time.Instant

/**
 * 단순 wrapping Model
 * 게시물, 댓글, 리액션 조합
 * @author Hyounglin Jun
 */
class Feed(
    val article: Article,
    val comments: List<Comment>,
) {

    class Article(
        val articleId: Int,
        val imageUrls: List<String>?,
        val body: String?,
        val writerUserId: String,
        val updatedAt: Instant,
        val createdAt: Instant,
        val reactions: List<Reaction>,
    )

    class Comment(
        var id: Long,
        var contents: String,
        val writerId: String,
        var createdAt: Instant,
        var updatedAt: Instant,
        val reaction: Reaction,
    )

    class Reaction(
        var id: Long,
        val type: ReactionType,
        val owner: Boolean,
    )
}
