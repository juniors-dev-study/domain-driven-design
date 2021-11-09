package com.sns.user.endpoints.user

import com.sns.user.endpoints.user.requests.SignUpRequest
import com.sns.user.endpoints.user.responses.IdExistsCheckResponse
import javax.validation.constraints.Email
import org.springframework.http.HttpStatus
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
@RequestMapping("/api")
class SignUpController {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/v1/sign-up")
    fun signUp(@RequestBody request: SignUpRequest) {
        // TODO 패스워드 유효성 검증
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/v1/sign-up/verifications/emails/{email}")
    fun verifyEmail(@Email @PathVariable email: String): IdExistsCheckResponse {
        // TODO email 중복 검사
        return IdExistsCheckResponse(false)
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/v1/sign-up/verifications/ids/{userId}/auth-code")
    fun createAuthenticationCode(@PathVariable userId: String) {
        // TODO 인증번호 재발송
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/v1/sign-up/verifications/ids/{userId}/auth-code")
    fun verifyAuthenticationCode(@PathVariable userId: String, @RequestBody code: String) {
        // TODO 인증번호 검사
    }
}
