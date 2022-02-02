package com.sns.user.endpoints.user

import com.sns.commons.annotation.IsLoginUser
import com.sns.commons.exceptions.NotExistException
import com.sns.commons.oauth.LoginUser
import com.sns.user.component.user.application.ProfileCommandService
import com.sns.user.component.user.application.ProfileQueryService
import com.sns.user.core.config.SwaggerTag
import com.sns.user.core.exceptions.NotExistException
import com.sns.user.endpoints.user.requests.UpdateProfileRequest
import com.sns.user.endpoints.user.requests.UpdateProfileRequest
import com.sns.user.endpoints.user.responses.ProfileResponse
import io.swagger.annotations.ApiOperation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * @author Hyounglin Jun
 */
@Validated
@RestController
@Tag(name = SwaggerTag.SIGN_UP)
@RequestMapping("/api")
class ProfileController(
    val profileQueryService: ProfileQueryService,
    val profileCommandService: ProfileCommandService,
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
                .orElseThrow { NotExistException("이메일 인증을 먼저 진행해주세요") },
        )
    }

    @ApiOperation("프로필 수정")
    @ApiResponses(
        value = [
            ApiResponse(description = "성공", responseCode = "202"),
            ApiResponse(description = "해당 유저가 없음", responseCode = "409"),
        ],
    )
    @ResponseStatus(HttpStatus.OK)
    @IsLoginUser
    @PutMapping("/v1/profiles")
    fun updateProfile(
        loginUser: LoginUser,
        @RequestBody request: UpdateProfileRequest,
    ) {
        val userId = loginUser.id ?: throw NotExistException()
        profileCommandService.update(userId, request.nickName, request.iconImageUrl, request.intro, request.hobbies)
    }
}
