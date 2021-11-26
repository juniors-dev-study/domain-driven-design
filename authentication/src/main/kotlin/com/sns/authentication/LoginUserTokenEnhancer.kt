package com.sns.authentication

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import org.springframework.stereotype.Component

/**
 * 토큰 payload에 데이터 추가시 사용.
 * 키 없이 열수있으니 민감한 데이터는 넣지마세요!
 */
@Component
class LoginUserTokenEnhancer : TokenEnhancer {
    override fun enhance(accessToken: OAuth2AccessToken, authentication: OAuth2Authentication): OAuth2AccessToken {
        val user = authentication.principal as LoginUser

        if (accessToken is DefaultOAuth2AccessToken) {
            val additionalInfo = mutableMapOf<String, Any>()
            additionalInfo["user_id"] = user.id
            accessToken.additionalInformation = additionalInfo
        }
        return accessToken
    }
}
