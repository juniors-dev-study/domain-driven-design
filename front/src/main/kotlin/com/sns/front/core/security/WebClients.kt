package com.sns.front.core.security

import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class WebClients(
    val clientRegistrationRepository: ClientRegistrationRepository,
    val oAuth2AuthorizedClientRepository: OAuth2AuthorizedClientRepository,
) {
    // web-flux 설정 필요. EnableWebFluxSecurity 로그인에러떠서 다음에 ㅎㅎ
    fun createOauthBuilder(): WebClient.Builder {
        val oauth = ServletOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrationRepository, oAuth2AuthorizedClientRepository)
        // val oauth = ServerOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrationRepository, serverOAuth2AuthorizedClientRepository)
        oauth.setDefaultOAuth2AuthorizedClient(true)
        return WebClient.builder()
            .filter(oauth)
    }
}
