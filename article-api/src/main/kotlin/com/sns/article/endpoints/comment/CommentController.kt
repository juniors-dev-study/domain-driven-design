package com.sns.article.endpoints.comment

import com.sns.article.component.comment.application.CommentCommandService
import com.sns.article.endpoints.comment.requests.CommentCreateRequest
import com.sns.article.endpoints.comment.requests.CommentUpdateRequest
import com.sns.commons.annotation.IsLoginUser
import com.sns.commons.config.SwaggerTag
import com.sns.commons.oauth.LoginUser
import io.swagger.annotations.ApiOperation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import javax.validation.Valid
import javax.validation.constraints.Positive
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@Tag(name = SwaggerTag.COMMENT)
@RequestMapping("/api/v1/comments")
class CommentV1Controller(
    private val commentCommandService: CommentCommandService
) {

    @ApiOperation("댓글 작성")
    @ApiResponses(
        value = [
            ApiResponse(description = "성공", responseCode = "202"),
            ApiResponse(description = "해당 유저가 없음", responseCode = "409"),
        ],
    )
    @IsLoginUser
    @PostMapping
    fun create(@Valid @RequestBody request: CommentCreateRequest, loginUser: LoginUser) {
        commentCommandService.create(request.rootType, request.rootId, request.contents, loginUser.id)
    }

    @ApiOperation("댓글 작성")
    @ApiResponses(
        value = [
            ApiResponse(description = "성공", responseCode = "202"),
            ApiResponse(description = "권한 없음", responseCode = "409"),
        ],
    )
    @IsLoginUser
    @PutMapping("/{commentId}")
    fun update(@Positive @PathVariable commentId: Long, @Valid @RequestBody request: CommentUpdateRequest, loginUser: LoginUser) {
        commentCommandService.update(commentId, request.contents, loginUser.id)
    }

    @ApiOperation("댓글 작성")
    @ApiResponses(
        value = [
            ApiResponse(description = "성공", responseCode = "202"),
            ApiResponse(description = "권한 없음", responseCode = "409"),
        ],
    )
    @IsLoginUser
    @DeleteMapping("/{commentId}")
    fun delete(@Positive @PathVariable commentId: Long, loginUser: LoginUser) {
        commentCommandService.delete(commentId, loginUser.id)
    }
}
