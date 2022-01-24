package com.sns.article.endpoints.request

import org.hibernate.validator.constraints.URL
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

/**
 * @author Hyounglin Jun
 */
class WriteArticleRequest(
    @NotEmpty
    @Size(max = 1000, message = "본문는 1000자 미만이어야 합니다.")
    val body: String,

    @Size(max = 10)
    @URL
    val imageUrls: MutableList<String>,
)
