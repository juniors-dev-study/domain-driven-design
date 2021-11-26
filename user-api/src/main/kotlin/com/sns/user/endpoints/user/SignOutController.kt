package com.sns.user.endpoints.user

import com.sns.commons.annotation.IsLoginUser
import com.sns.user.component.user.application.UserCommandService
import com.sns.user.core.supports.securities.authentications.CurrentUser
import com.sns.user.core.supports.securities.authentications.LoginUser
import io.swagger.annotations.ApiOperation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class SignOutController(
    val userCommandService: UserCommandService
) {

    @IsLoginUser
    @ApiOperation("회원 탈퇴")
    @ApiResponses(
        value = [
            ApiResponse(description = "성공", responseCode = "202"),
            ApiResponse(description = "이미 존재하는 유저", responseCode = "409"),
        ],
    )
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/v1/sign-out")
    fun signOut(@CurrentUser loginUser: LoginUser) {
        userCommandService.delete(loginUser.id)
    }
}
