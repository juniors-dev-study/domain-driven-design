package com.sns.front.core.security

import javax.servlet.http.HttpSession
import org.slf4j.LoggerFactory
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class OauthUserService(
    val httpSession: HttpSession
) : DefaultOAuth2UserService() {
    val log = LoggerFactory.getLogger(this.javaClass)

    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        val clientId = userRequest!!.clientRegistration.clientId
        val oAuth2User = super.loadUser(userRequest)
        httpSession.setAttribute(USER_SESSION_ID, oAuth2User.name)
        log.error("authUser : {}", oAuth2User.name)
        return oAuth2User
    }

    companion object {
        const val USER_SESSION_ID = "USER_SESSION"
    }
}

class LoginUser(
    val id: String,
    val name: String,
    val email: String
)
