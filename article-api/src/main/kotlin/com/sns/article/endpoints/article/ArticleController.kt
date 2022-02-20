package com.sns.article.endpoints.article

import com.sns.article.component.article.application.ArticleCommandService
import com.sns.article.component.article.application.ArticleQueryService
import com.sns.article.component.article.domains.ArticleId
import com.sns.article.endpoints.request.WriteArticleRequest
import com.sns.article.endpoints.response.ArticleResponse
import com.sns.article.endpoints.response.ArticleScopesResponse
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
    @ApiOperation("내 게시물 조회")
    @ApiResponses(
        value = [
            ApiResponse(description = "성공", responseCode = "202"),
            ApiResponse(description = "해당 유저가 없음", responseCode = "409"),
        ],
    )
    @IsLoginUser
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/v1/articles/{articleId}")
    fun getMyArticle(
        loginUser: LoginUser,
        @PathVariable articleId: Int,
    ): ArticleResponse {
        return ArticleResponse(articleQueryService.getMyArticle(loginUser.id, ArticleId(articleId)))
    }

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

    @ApiOperation("게시물 공개범위 목록 조회")
    @ApiResponses(
        value = [
            ApiResponse(description = "성공", responseCode = "202"),
            ApiResponse(description = "해당 유저가 없음", responseCode = "409"),
        ],
    )
    @IsLoginUser
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/v1/articles/scopes")
    fun getArticleScopes(): ArticleScopesResponse {
        return ArticleScopesResponse
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
        articleCommandService.create(loginUser.id, request.body, request.imageUrls, request.scope)
    }

    @ApiOperation("게시물 수정")
    @ApiResponses(
        value = [
            ApiResponse(description = "성공", responseCode = "202"),
            ApiResponse(description = "해당 유저가 없음", responseCode = "409"),
        ],
    )
    @IsLoginUser
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/v1/articles/{articleId}")
    fun modifyArticle(
        @PathVariable articleId: Int,
        loginUser: LoginUser,
        @RequestBody request: WriteArticleRequest,
    ) {
        articleCommandService.modify(ArticleId(articleId), loginUser.id, request.body, request.imageUrls)
    }

    @ApiOperation("게시물 삭제 (연관 좋아요, 댓글 포함)")
    @ApiResponses(
        value = [
            ApiResponse(description = "성공", responseCode = "202"),
            ApiResponse(description = "해당 유저가 없음", responseCode = "409"),
        ],
    )
    @IsLoginUser
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/v1/articles/{articleId}")
    fun deleteArticle(
        @PathVariable articleId: Int,
        loginUser: LoginUser,
    ) {
        articleCommandService.delete(loginUser.id, ArticleId(articleId))
    }
}
