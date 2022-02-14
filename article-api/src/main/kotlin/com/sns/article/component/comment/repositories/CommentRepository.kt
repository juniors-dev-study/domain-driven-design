package com.sns.article.component.comment.repositories

import com.sns.article.component.comment.domains.Comment
import com.sns.article.component.comment.domains.RootType
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface CommentRepository : CrudRepository<Comment, Long> {
    fun findAllByRootIdInAndRootType(rootIds: List<String>, rootType: RootType): List<Comment>
}
