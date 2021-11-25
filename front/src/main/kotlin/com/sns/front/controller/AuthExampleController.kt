package com.sns.front.controller

import com.sns.front.core.security.IsLoginUser
import com.sns.front.utils.log
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthExampleController {
    val log = log()

    @IsLoginUser
    @GetMapping("/auth/example")
    fun profile(@AuthenticationPrincipal user: OAuth2User): String {
        log.error("user : {} , {}", user.name, user.authorities)
        return "pages/profile"
    }
}
