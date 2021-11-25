package com.sns.front.controller

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import reactor.core.publisher.Mono
import javax.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.WebClient

@RequestMapping("/auth-api")
@Controller
class SecurityController() {

    val log: Logger = LoggerFactory.getLogger(this.javaClass)
    val objectMapper = ObjectMapper()
    val webClient = WebClient.builder()
        .baseUrl("http://local-auth.ddd.sns.com:10010/")
        .defaultHeaders { h -> h.setBasicAuth("front_client", "secret") }
        .filter(
            ExchangeFilterFunction.ofResponseProcessor {
                log.debug("{}", it.rawStatusCode())
                Mono.just(it)
            },
        )
        .build()

    @GetMapping("/v1/callback")
    fun home(@RequestParam code: String, httpServletRequest: HttpServletRequest): String {
        log.error("auth_code : {}", code)
        val tokenResponse = webClient
            .post()
            .uri("/oauth/token")
            .body(BodyInserters.fromFormData(TokenRequest(code = code).toMap()))
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(HttpStatus::isError) { Mono.error(Exception(it.rawStatusCode().toString())) }
            .bodyToMono(TokenResponse::class.java)
            .doOnError { e -> log.error(e.message, e) }
            .doOnNext { r -> log.debug(r.toString()) }
            .block()

        val session = httpServletRequest.session
        session.setAttribute("J", tokenResponse!!.accessToken)

        return "redirect:/home"
    }
}

class TokenRequest(
    val grantType: String = "authorization_code",
    val clientId: String = "front_client",
    val scope: String = "read",
    val code: String
) {
    fun toMap(): MultiValueMap<String, String> =
        LinkedMultiValueMap(
            mutableMapOf(
                "grant_type" to mutableListOf(grantType),
                "client_id" to mutableListOf(clientId),
                "scope" to mutableListOf(scope),
                "code" to mutableListOf(code),
                "redirect_uri" to mutableListOf("http://localhost:10100/auth-api/v1/callback"),
            ),
        )
}

class TokenResponse(
    @JsonProperty("access_token")
    var accessToken: String? = "",
    @JsonProperty("token_type")
    var tokenType: String? = "",
    @JsonProperty("expires_in")
    var expiresIn: Int? = 0,
    @JsonProperty("scope")
    var scope: String? = "",
    @JsonProperty("jti", required = false)
    var jti: String? = ""
) {
    override fun toString(): String {
        return "token : $accessToken , type : $tokenType , expiresIn : $expiresIn , scope : $scope"
    }
}
