package com.sns.article.endpoints.comment

import com.sns.article.component.comment.application.CommentCommandService
import com.sns.article.component.comment.domains.Comment
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
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
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
    private val commentCommand: CommentCommand,
    private val commentQuery: CommentQuery
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
    fun create(@Valid @RequestBody request: CommentCreateRequest, loginUser: LoginUser) =
        commentCommand.create(request.rootType, request.rootId, request.contents, loginUser.id)

    @ApiOperation("댓글 수정")
    @ApiResponses(
        value = [
            ApiResponse(description = "성공", responseCode = "202"),
            ApiResponse(description = "권한 없음", responseCode = "409"),
        ],
    )
    @IsLoginUser
    @PutMapping("/{commentId}")
    fun updateContents(@Positive @PathVariable commentId: Long, @Valid @RequestBody request: CommentUpdateRequest, loginUser: LoginUser) =
        commentCommand.updateContents(commentId, request.contents, loginUser.id)

    @ApiOperation("댓글 작성")
    @ApiResponses(
        value = [
            ApiResponse(description = "성공", responseCode = "202"),
            ApiResponse(description = "권한 없음", responseCode = "409"),
        ],
    )
    @IsLoginUser
    @DeleteMapping("/{commentId}")
    fun delete(@Positive @PathVariable commentId: Long, loginUser: LoginUser) = commentCommand.delete(commentId, loginUser.id)

    @ApiOperation("댓글 조회 - 부모 노드 정보로 조회")
    @ApiResponses(
        value = [
            ApiResponse(description = "성공", responseCode = "202"),
            ApiResponse(description = "권한 없음", responseCode = "409"),
        ],
    )
    @GetMapping("/roots/{rootType}:{rootId}")
    fun getByRootId(@PathVariable @NotNull rootType: Comment.Root.Type, @PathVariable @NotBlank rootId: String) =
        commentQuery.getByRoot(Comment.Root(rootType, rootId))

    @ApiOperation("댓글 조회 - 로그인 유저의 댓글 조회")
    @ApiResponses(
        value = [
            ApiResponse(description = "성공", responseCode = "202"),
            ApiResponse(description = "권한 없음", responseCode = "409"),
        ],
    )
    @IsLoginUser
    @GetMapping("/my")
    fun getByWriterId(loginUser: LoginUser) =
        commentQuery.getByWriterId(loginUser.id)
}
