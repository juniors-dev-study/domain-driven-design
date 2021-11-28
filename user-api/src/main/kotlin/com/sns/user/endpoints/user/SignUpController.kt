package com.sns.user.endpoints.user

import com.sns.commons.utils.ifTrue
import com.sns.user.component.authcode.application.AuthCodeCommandService
import com.sns.user.component.authcode.domain.Purpose
import com.sns.user.component.user.application.ProfileCommandService
import com.sns.user.component.user.application.UserCommandService
import com.sns.user.component.user.application.UserQueryService
import com.sns.user.core.config.SwaggerTag
import com.sns.user.core.exceptions.NoAuthorityException
import com.sns.user.endpoints.user.requests.SignUpRequest
import com.sns.user.endpoints.user.responses.SignUpVerifiedResponse
import io.swagger.annotations.ApiOperation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.Email

@Validated
@CrossOrigin(origins = listof("http://localhost:10100")) // 추가
@RestController
@Tag(name = SwaggerTag.SIGN_UP)
@RequestMapping("/api")
class SignUpController(
    val authCodeCommandService: AuthCodeCommandService,
    val userQueryService: UserQueryService,
    val userCommandService: UserCommandService,
    val signUpAggregator: SignUpAggregator
) {

    @ApiOperation("회원 가입")
    @ApiResponses(
        value = [
            ApiResponse(description = "성공", responseCode = "202"),
            ApiResponse(description = "이미 존재하는 유저", responseCode = "409"),
        ],
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/v1/sign-up")
    fun signUp(@RequestBody request: SignUpRequest) {
        userCommandService.create(request.name, request.password, request.email)
    }

    @ApiOperation("이메일 중복 검사")
    @ApiResponse(
        description = "이메일 중복 검사 통과 여부", responseCode = "200",
        content = [Content(schema = Schema(implementation = Boolean::class))],
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/v1/sign-up/verifications/emails/{email}")
    fun verifyEmail(@Email @PathVariable email: String): SignUpVerifiedResponse {
        return SignUpVerifiedResponse(userQueryService.getByEmail(email) != null)
    }

    @ApiOperation("가입 인증 코드 재발송")
    @ApiResponse(description = "성공", responseCode = "202")
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/v1/sign-up/verifications/auth-code/ids/{userId}")
    fun createAuthenticationCode(@PathVariable userId: String) {
        val user = userQueryService.getById(userId) ?: throw NoAuthorityException()
        authCodeCommandService.create(user)
    }

    @ApiOperation("가입 인증 코드 검사")
    @ApiResponse(
        description = "가입 인증 성공", responseCode = "200",
        content = [Content(schema = Schema(implementation = Boolean::class))],
    )
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/v1/sign-up/verifications/auth-code/ids/{userId}")
    fun verifyAuthenticationCode(@PathVariable userId: String, @RequestBody code: String): SignUpVerifiedResponse {
        return SignUpVerifiedResponse(signUpAggregator.verifyAuthentication(userId, code))
    }
}

@Component
class SignUpAggregator(
    val authCodeCommandService: AuthCodeCommandService,
    val userCommandService: UserCommandService,
    val profileCommandService: ProfileCommandService,
) {

    @Transactional
    fun verifyAuthentication(userId: String, code: String): Boolean =
        authCodeCommandService.verify(userId, Purpose.SIGN_UP, code)
            .ifTrue {
                userCommandService.activate(userId)
                profileCommandService.create(userId)
            } ?: false
}
