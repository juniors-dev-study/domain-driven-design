package com.sns.front.controller

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

/**
 * User 관련된 페이지 모음
 * @author Hyounglin Jun
 */
@Controller
class UserController {

    @GetMapping("/home", "/")
    fun home(
        @AuthenticationPrincipal user: OAuth2User?,
    ): String {
        return "pages/home"
    }

    @GetMapping("/login")
    fun login(
        @AuthenticationPrincipal user: OAuth2User?,
    ): String {

        return "pages/login"
    }

    @GetMapping("/register")
    fun register(): String {
        return "pages/register"
    }

    @GetMapping("/profile")
    fun profile(
        @AuthenticationPrincipal user: OAuth2User?,
    ): String {
        return "pages/profile"
    }
}
