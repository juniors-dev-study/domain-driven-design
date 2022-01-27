package com.sns.user.endpoints.user

import com.sns.commons.annotation.IsLoginUser
import com.sns.commons.exceptions.NotExistException
import com.sns.commons.oauth.LoginUser
import com.sns.user.component.user.application.ProfileQueryService
import com.sns.user.core.config.SwaggerTag
import com.sns.user.endpoints.user.responses.ProfileResponse
import io.swagger.annotations.ApiOperation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

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
    @IsLoginUser
    @GetMapping("/v1/profiles")
    fun getProfile(loginUser: LoginUser): ProfileResponse {
        return ProfileResponse(
            profileQueryService.getById(loginUser.id ?: "")
                .orElseThrow { NotExistException() },
        )
    }
}
