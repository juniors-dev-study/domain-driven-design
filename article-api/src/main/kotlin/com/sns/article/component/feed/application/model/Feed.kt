package com.sns.article.component.feed

import com.sns.article.component.article.domains.Article
import com.sns.article.component.article.domains.ArticleId
import com.sns.article.component.comment.domains.Comment
import com.sns.article.component.comment.domains.RootType
import com.sns.article.component.reaction.domains.Reaction
import com.sns.article.component.reaction.domains.ReactionType
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

/**
 * 단순 wrapping Model
 * 게시물, 댓글, 리액션 조합
 * @author Hyounglin Jun (KR19849)
 */
class Feed(
    val article: FeedArticle,
    val comments: List<FeedComment>,
) {

}

// TODO 이 객체를 어디다 둘지 고민중
data class FeedArticle(
    val articleId: ArticleId?,
    val imageUrls: MutableList<String>?,
    val body: String?,
    val writerUserId: String,
    val updatedAt: Instant,
    val createdAt: Instant,
    val reaction: FeedReaction,
)

// TODO 이 객체를 어디다 둘지 고민중
data class FeedComment(
    var id: Long? = null,
    var contents: String,
    val writerId: String,
    var createdAt: Instant,
    var updatedAt: Instant,
    val reaction: FeedReaction,
)

data class FeedReaction(
    var id: Long?,
    val type: ReactionType,
    val userId: String,
)
