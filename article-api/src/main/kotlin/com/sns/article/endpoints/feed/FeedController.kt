package com.sns.article.endpoints.feed

import com.sns.article.component.article.application.ArticleCommandService
import com.sns.article.component.article.application.ArticleQueryService
import com.sns.article.component.feed.application.FeedQueryService
import com.sns.article.endpoints.response.ArticlesResponse
import com.sns.article.endpoints.response.FeedsResponse
import com.sns.commons.annotation.IsLoginUser
import com.sns.commons.oauth.LoginUser
import io.swagger.annotations.ApiOperation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

/**
 * @author Hyounglin Jun (KR19849)
 */
@RestController
@RequestMapping("/api")
class FeedController(
    val feedQueryService: FeedQueryService,
) {
    @ApiOperation("피드 목록 조회")
    @ApiResponses(
        value = [
            ApiResponse(description = "성공", responseCode = "202"),
            ApiResponse(description = "해당 유저가 없음", responseCode = "409"),
        ],
    )
    @IsLoginUser
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/v1/feeds")
    fun getFeeds(loginUser: LoginUser): FeedsResponse {
        // return FeedsResponse.create(feedQueryService.getFeeds(loginUserId = loginUser.id))
        return FeedsResponse.createMock()
    }
}
