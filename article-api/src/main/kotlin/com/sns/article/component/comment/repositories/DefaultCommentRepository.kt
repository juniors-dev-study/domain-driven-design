package com.sns.article.component.comment.repositories

import org.springframework.stereotype.Repository

@Repository
class DefaultCommentRepository(
    commentCrudRepository: CommentCrudRepository
) : CommentRepository,
    CommentCrudRepository by commentCrudRepository
