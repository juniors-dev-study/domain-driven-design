package com.sns.front.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

/**
 * User 관련된 페이지 모음
 * @author Hyounglin Jun
 */
@Controller
class UserController {
    @GetMapping("/home")
    fun home(): String {
        return "pages/home"
    }

    @GetMapping("/login")
    fun login(): String {
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
}
