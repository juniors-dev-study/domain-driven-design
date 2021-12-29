package com.sns.front.core.security

import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class WebClients(
    val clientRegistrationRepository: ReactiveClientRegistrationRepository,
    val serverOAuth2AuthorizedClientRepository: ServerOAuth2AuthorizedClientRepository,
) {
    fun createOauthBuilder(): WebClient.Builder {
        val oauth = ServerOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrationRepository, serverOAuth2AuthorizedClientRepository)
        oauth.setDefaultOAuth2AuthorizedClient(true)
        return WebClient.builder()
            .filter(oauth)
    }
}
