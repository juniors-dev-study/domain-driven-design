package com.sns.user.endpoints.user

import com.sns.user.component.user.application.ProfileQueryService
import com.sns.user.core.config.SwaggerTag
import com.sns.user.core.exceptions.NotExistException
import com.sns.user.endpoints.user.responses.ProfileResponse
import io.swagger.annotations.ApiOperation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.Email

/**
 * @author Hyounglin Jun
 */
@Validated
@RestController
@Tag(name = SwaggerTag.SIGN_UP)
@RequestMapping("/api")
class ProfileController(
    val profileQueryService: ProfileQueryService,
) {
    @ApiOperation("프로필 조회")
    @ApiResponses(
        value = [
            ApiResponse(description = "성공", responseCode = "202"),
            ApiResponse(description = "해당 유저가 없음", responseCode = "409"),
        ],
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/v1/profiles/{userId}")
    fun getProfile(@Email @PathVariable userId: String): ProfileResponse {
        return ProfileResponse(
            profileQueryService.getById(userId)
                .orElseThrow { NotExistException() },
        )
    }
}
