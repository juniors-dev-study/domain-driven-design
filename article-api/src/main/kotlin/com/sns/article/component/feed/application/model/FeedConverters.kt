package com.sns.article.component.feed.application.model

import com.sns.article.component.article.domains.Article
import com.sns.article.component.feed.Feed
import com.sns.article.component.reaction.domains.Reaction
import com.sns.commons.utils.notNull

fun Article.toFeedArticle(loginUserId: String, reactions: List<Reaction>): Feed.Article =
    Feed.Article(
        articleId = articleId.notNull().id,
        imageUrls = imageUrls,
        body = body,
        writerUserId = writerUserId,
        updatedAt = updatedAt,
        createdAt = createdAt,
        reactions = reactions.map { it.toFeedReaction(loginUserId) },
    )

fun Reaction.toFeedReaction(loginUserId: String): Feed.Reaction =
    Feed.Reaction(
        id = id.notNull(),
        type = type,
        owner = userId == loginUserId,
    )
