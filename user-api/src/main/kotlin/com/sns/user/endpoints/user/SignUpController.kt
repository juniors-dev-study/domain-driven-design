package com.sns.user.endpoints.user

import com.sns.commons.utils.ifTrue
import com.sns.user.component.authcode.application.AuthCodeCommand
import com.sns.user.component.authcode.domain.Purpose
import com.sns.user.component.user.application.UserCommandService
import com.sns.user.component.user.application.UserQueryService
import com.sns.user.core.config.SwaggerTag
import com.sns.user.endpoints.user.requests.SignUpRequest
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import javax.validation.constraints.Email
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@Tag(name = SwaggerTag.SIGN_UP)
@RequestMapping("/api")
class SignUpController(
    val authCodeCommand: AuthCodeCommand,
    val userQueryService: UserQueryService,
    val userCommandService: UserCommandService
) {

    @ApiResponse(description = "회원 가입", responseCode = "202")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/v1/sign-up")
    fun signUp(@RequestBody request: SignUpRequest) {
        userCommandService.create(request.name, request.password, request.email)
    }

    @ApiResponse(
        description = "이메일 중복 검사", responseCode = "200",
        content = [Content(schema = Schema(implementation = Boolean::class))],
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/v1/sign-up/verifications/emails/{email}")
    fun verifyEmail(@Email @PathVariable email: String): ResponseEntity<Boolean> {
        return (userQueryService.getByEmail(email) != null)
            .let { ResponseEntity.ok(it) }
    }

    @ApiResponse(description = "가입 인증 코드 재발송", responseCode = "202")
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/v1/sign-up/verifications/auth-code/ids/{userId}")
    fun createAuthenticationCode(@PathVariable userId: String) {
        authCodeCommand.create(userId)
    }

    @ApiResponse(
        description = "가입 인증 코드 검사", responseCode = "200",
        content = [Content(schema = Schema(implementation = Boolean::class))],
    )
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/v1/sign-up/verifications/auth-code/ids/{userId}")
    fun verifyAuthenticationCode(@PathVariable userId: String, @RequestBody code: String): ResponseEntity<Boolean> {
        return authCodeCommand.verify(userId, Purpose.SIGN_UP, code)
            .ifTrue { userCommandService.activate(userId) }
            .let {
                ResponseEntity.ok(it)
            }
    }
}
