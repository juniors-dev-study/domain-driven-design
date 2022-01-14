package com.sns.article.endpoints

import com.sns.commons.annotation.IsLoginUser
import com.sns.commons.oauth.LoginUser
import io.swagger.annotations.ApiOperation
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
@RequestMapping("/api")
class TestController(
) {
    @ApiOperation("테스트 입니다")
    @ResponseStatus(HttpStatus.OK)
    @IsLoginUser
    @GetMapping("/v1/test")
    fun getTest(loginUser: LoginUser): String {
        println(loginUser)
        return ""
    }

    @ApiOperation("테스트 입니다")
    @ResponseStatus(HttpStatus.OK)
    @IsLoginUser
    @GetMapping("/test")
    fun test(): String {
        return ""
    }
}
