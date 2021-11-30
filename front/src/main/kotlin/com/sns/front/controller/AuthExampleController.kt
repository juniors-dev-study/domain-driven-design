package com.sns.front.controller

import com.sns.front.core.security.IsLoginUser
import com.sns.front.core.security.WebClients
import com.sns.front.utils.log
import reactor.core.publisher.Mono
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.ExchangeFilterFunction

@RestController
class AuthExampleController(
    val webClients: WebClients
) {
    val log = log()
    val userWebClient = webClients.createOauthBuilder().baseUrl("http://localhost:10001")
        .filter(
            ExchangeFilterFunction.ofRequestProcessor {
                log.error("headers : {}", it.headers())
                log.error("attrs : {}", it.attributes())
                Mono.just(it)
            },
        )
        .build()

    @IsLoginUser
    @GetMapping("/auth/example")
    fun checkLogin(
        @RegisteredOAuth2AuthorizedClient authorizedClient: OAuth2AuthorizedClient,
        @AuthenticationPrincipal user: OAuth2User
    ): String {
        log.error("user : {} , {}", user.name, user.authorities)
        log.error("client : {} , {}", authorizedClient.principalName)

        return userWebClient
            .get().uri("/api/v1/users/checkLogin")
            .headers { it.setBearerAuth(authorizedClient.accessToken.tokenValue) }
            // .attributes(ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient(authorizedClient))
            .retrieve()
            .onStatus(HttpStatus::isError) { it.bodyToMono(String::class.java).map { r -> RuntimeException(r) } }
            .bodyToMono(String::class.java)
            .doOnError { log.error(it.message) }
            .block() ?: "Hi ${user.name}, this is front"
    }
}
