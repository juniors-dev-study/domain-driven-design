package com.sns.user.endpoints.user

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class SignOutController {

    @PostMapping("/v1/sign-out")
    fun signOut() {
        // TODO 탈퇴
    }
}
