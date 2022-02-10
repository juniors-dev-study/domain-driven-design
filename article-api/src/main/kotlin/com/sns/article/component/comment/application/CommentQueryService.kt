package com.sns.article.component.comment.application

import com.sns.article.component.comment.repositories.CommentCrudRepository
import org.springframework.stereotype.Service

@Service
class CommentQueryService(val commentCrudRepository: CommentCrudRepository) {

}
