package com.sns.authentication.config
//
// import com.sns.authentication.ReactiveLoginUserService
// import org.slf4j.LoggerFactory
// import org.springframework.beans.factory.annotation.Value
// import org.springframework.context.annotation.Bean
// import org.springframework.context.annotation.Configuration
// import org.springframework.core.io.FileSystemResource
// import org.springframework.security.authentication.ReactiveAuthenticationManager
// import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
// import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
// import org.springframework.security.config.web.server.ServerHttpSecurity
// import org.springframework.security.core.Authentication
// import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
// import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory
// import org.springframework.security.web.server.SecurityWebFilterChain
// import org.springframework.stereotype.Component
// import org.springframework.web.cors.CorsConfiguration
// import org.springframework.web.cors.reactive.CorsConfigurationSource
// import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
// import reactor.core.publisher.Mono
//
// /**
//  * @author Hyounglin Jun
//  */
// @EnableWebFluxSecurity
// class WebFluxSecurityConfig {
//
//     @Bean
//     fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain? {
//         http
//             .authorizeExchange().pathMatchers("/", "/oauth/**", "/h2-console/**").permitAll().and() // 인증이 필요 없는 path
//             .authorizeExchange().anyExchange().authenticated().and()    // 나머지 path는 모두 인증 필요
//             .logout().logoutUrl("/logout").and() // 로그아웃 로직 더 봐야함
//             .formLogin().and()  // 기본 로그인 폼 사용
//             .csrf().disable()   // csrf 취약점 방어
//
//         return http.build()
//     }
//
//     // @Bean
//     // fun authenticationTokenConverter(): ServerAuthenticationConverter = AuthenticationTokenConverter()
//
//     @Bean
//     fun authenticationManager(reactiveLoginUserService: ReactiveLoginUserService?): ReactiveAuthenticationManager? {
//         return UserDetailsRepositoryReactiveAuthenticationManager(reactiveLoginUserService)
//     }
//
//     @Bean
//     fun corsConfigurationSource(): CorsConfigurationSource? {
//         val source = UrlBasedCorsConfigurationSource()
//         source.registerCorsConfiguration("/**", CorsConfiguration())
//         return source
//     }
// }
//
// @Component
// class AuthenticationManager : ReactiveAuthenticationManager {
//     override fun authenticate(authentication: Authentication?): Mono<Authentication> {
//
//         val jwtToken = authentication?.credentials.toString()
//         return Mono.just(authentication)
//     }
// }
//
// @Configuration
// class OAuth2ConfigBean {
//     @Bean
//     fun jwtAccessTokenConverter(
//         @Value("\${security.oauth2.resource.jwt.custom-jks-path}") keyPath: String,
//         @Value("\${security.oauth2.resource.jwt.custom-jks-passwd}") passwd: String,
//         @Value("\${security.oauth2.resource.jwt.custom-jks-alias}") alias: String,
//     ): JwtAccessTokenConverter {
//         LoggerFactory.getLogger(this.javaClass).error("passwd : {}", passwd)
//         val keyStoreKeyFactory = KeyStoreKeyFactory(FileSystemResource(keyPath), passwd.toCharArray())
//         val accessTokenConverter = JwtAccessTokenConverter()
//         // accessTokenConverter.setSigningKey(resourceServerProperties.jwt.keyValue)
//         accessTokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair(alias))
//         return accessTokenConverter
//     }
// }
