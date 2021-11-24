package com.sns.user.endpoints.user

import com.sns.user.component.user.application.UserFriendService
import com.sns.user.core.supports.securities.authentications.loginUser
import com.sns.user.endpoints.user.requests.UserFriendRequestCreateRequest
import com.sns.user.endpoints.user.responses.DefaultSuccessResponse
import com.sns.user.endpoints.user.responses.IdCreatedResponse
import io.swagger.annotations.ApiOperation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users/friends")
class UserFriendController(
    val userFriendService: UserFriendService
) {

    @ApiOperation("친구 끊기")
    @ApiResponses(
        value = [
            ApiResponse(description = "성공", responseCode = "201"),
            ApiResponse(description = "이미 존재하는 친구 요청 / 이미 친구 상태", responseCode = "409"),
        ],
    )
    @ResponseStatus(HttpStatus.CREATED)
    @DeleteMapping("/users/{friendUserId}")
    fun deleteFriend(@PathVariable friendUserId: String, authentication: Authentication): DefaultSuccessResponse {
        val loginUser = authentication.loginUser()

        userFriendService.breakFriendship(loginUser.user.id, friendUserId)

        return DefaultSuccessResponse
    }

    @ApiOperation("친구 요청")
    @ApiResponses(
        value = [
            ApiResponse(description = "성공", responseCode = "201"),
            ApiResponse(description = "이미 존재하는 친구 요청 / 이미 친구 상태", responseCode = "409"),
        ],
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/requests")
    fun createRequest(@RequestBody request: UserFriendRequestCreateRequest, authentication: Authentication): IdCreatedResponse<Long> {
        val loginUser = authentication.loginUser()

        return IdCreatedResponse(
            userFriendService.createFriendRequest(loginUser.user.id, request.targetUserId).id,
        )
    }

    @ApiOperation("친구 요청 제거")
    @ApiResponses(
        value = [
            ApiResponse(description = "성공", responseCode = "201"),
            ApiResponse(description = "이미 존재하는 친구 요청 / 이미 친구 상태", responseCode = "409"),
        ],
    )
    @DeleteMapping("/requests/{requestId}")
    fun deleteRequest(@PathVariable requestId: Long, authentication: Authentication): DefaultSuccessResponse {
        val loginUser = authentication.loginUser()

        userFriendService.deleteFriendRequest(loginUser.user.id, requestId)

        return DefaultSuccessResponse
    }

    @ApiOperation("친구 요청 처리")
    @ApiResponses(
        value = [
            ApiResponse(description = "성공", responseCode = "201"),
            ApiResponse(description = "이미 존재하는 친구 요청 / 이미 친구 상태", responseCode = "409"),
        ],
    )
    @PatchMapping("/requests/{requestId}")
    fun handleRequest(@PathVariable requestId: Long, @RequestParam approve: Boolean, authentication: Authentication): DefaultSuccessResponse {
        val loginUser = authentication.loginUser()

        if (approve) {
            userFriendService.approveFriendRequest(loginUser.user.id, requestId)
        } else {
            userFriendService.rejectFriendRequest(loginUser.user.id, requestId)
        }

        return DefaultSuccessResponse
    }
}
