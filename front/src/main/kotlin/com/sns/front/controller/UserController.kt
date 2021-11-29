package com.sns.front.controller

import com.sns.front.core.security.WebClients
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

/**
 * User 관련된 페이지 모음
 * @author Hyounglin Jun
 */
@Controller
data class UserController(
    val oAuth2AuthorizedClientService: OAuth2AuthorizedClientService,
    val webClients: WebClients,
) {

    @GetMapping("/home", "/")
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

    @GetMapping("/api/profile")
    private fun getProfile(): String {
        val res: String = webClients.createOauthBuilder().build()
            .get()
            .uri("http://localhost:10001/api/v1/profiles")
            .retrieve()
            .bodyToMono(String::class.java).block() ?: ""
        println(res)
        return res;
    }
}
