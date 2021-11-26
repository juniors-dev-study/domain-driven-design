package com.sns.front.core.security

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class WebClients(
    // val clientRegistrationRepository: ReactiveClientRegistrationRepository,
    // val serverOAuth2AuthorizedClientRepository: ServerOAuth2AuthorizedClientRepository
) {
    // web-flux 설정 필요. EnableWebFluxSecurity 로그인에러떠서 다음에 ㅎㅎ
    fun createOauthBuilder(): WebClient.Builder {
        // val oauth = ServerOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrationRepository, serverOAuth2AuthorizedClientRepository)
        // oauth.setDefaultOAuth2AuthorizedClient(true)
        return WebClient.builder()
        // .filter(oauth)
    }
}
