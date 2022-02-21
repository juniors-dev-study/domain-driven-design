package com.sns.article.endpoints.response

import com.sns.article.component.article.domains.ArticleScope

object ArticleScopesResponse {
    val scopes: List<ArticleScopeResponse> = ArticleScope.values().map { ArticleScopeResponse(it) }

    data class ArticleScopeResponse(val value: ArticleScope, val text: String) {
        constructor(value: ArticleScope): this(value, value.text)
    }
}
