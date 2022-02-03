package com.sns.article.endpoints.article

import com.sns.article.component.article.application.ArticleCommandService
import com.sns.article.component.article.application.ArticleQueryService
import com.sns.article.endpoints.request.WriteArticleRequest
import com.sns.article.endpoints.response.ArticlesResponse
import com.sns.commons.annotation.IsLoginUser
import com.sns.commons.oauth.LoginUser
import io.swagger.annotations.ApiOperation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * @author Hyounglin Jun
 */
@Validated
@RestController
@RequestMapping("/api")
class ArticleController(
    val articleCommandService: ArticleCommandService,
    val articleQueryService: ArticleQueryService,
) {
    @ApiOperation("게시물 목록 조회")
    @ApiResponses(
        value = [
            ApiResponse(description = "성공", responseCode = "202"),
            ApiResponse(description = "해당 유저가 없음", responseCode = "409"),
        ],
    )
    @IsLoginUser
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/v1/articles")
    fun getArticles(loginUser: LoginUser): ArticlesResponse {
        return ArticlesResponse.create(articleQueryService.getArticles(loginUser.id))
    }

    @ApiOperation("게시물 작성")
    @ApiResponses(
        value = [
            ApiResponse(description = "성공", responseCode = "202"),
            ApiResponse(description = "해당 유저가 없음", responseCode = "409"),
        ],
    )
    @IsLoginUser
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/v1/articles")
    fun writeArticle(
        loginUser: LoginUser,
        @RequestBody request: WriteArticleRequest,
    ) {
        articleCommandService.create(loginUser.id, request.body, request.imageUrls)
    }
}
