package com.sns.article.endpoints.feed

import com.sns.article.component.feed.application.FeedQueryService
import com.sns.article.endpoints.response.FeedsResponse
import com.sns.commons.annotation.IsLoginUser
import com.sns.commons.oauth.LoginUser
import io.swagger.annotations.ApiOperation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import java.time.Instant
import javax.validation.constraints.Max
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

/**
 * @author Hyounglin Jun
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
    fun getFeeds(loginUser: LoginUser, @RequestParam lastUpdatedAt: Instant, @RequestParam @Max(100) count: Int): FeedsResponse {
        return FeedsResponse.create(feedQueryService.getFeeds(loginUserId = loginUser.id, lastUpdatedAt, count))
    }
}
