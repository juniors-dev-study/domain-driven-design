package com.sns.article.component.article.repositories

import org.springframework.stereotype.Repository

@Repository
class DefaultArticleRepository(
    articleCrudRepository: ArticleCrudRepository,
) : ArticleRepository,
    ArticleCrudRepository by articleCrudRepository
