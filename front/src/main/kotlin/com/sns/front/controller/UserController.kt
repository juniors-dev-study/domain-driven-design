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
    fun profile(): String {
        return "pages/profile"
    }

    /*
    * 회원 정보 수정 페이지
    * - 프로필 수정
     */
    @GetMapping("/modify-user")
    fun modifyUser(): String {
        return "pages/modify-user"
    }
}
