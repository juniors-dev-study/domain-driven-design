package com.sns.front.core.security
//
// import org.slf4j.LoggerFactory
// import org.springframework.security.core.GrantedAuthority
// import org.springframework.security.core.authority.SimpleGrantedAuthority
// import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
// import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
// import org.springframework.security.oauth2.core.user.DefaultOAuth2User
// import org.springframework.security.oauth2.core.user.OAuth2User
// import org.springframework.stereotype.Service
//
// // @Service
// // class OauthUserService() : OAuth2UserService<OAuth2UserRequest, OAuth2User> {
// //     val log = LoggerFactory.getLogger(this.javaClass)!!
// //
// //     /**
// //      * 토큰에 데이터를 충분히 가지고있기때문에, userInfo 엔드포인트조회 생략.
// //      * 추후 조회 필요시 DefaultOauth2UserService extends 로 구현가능.
// //      */
// //     override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
// //         val clientId = userRequest!!.clientRegistration.clientId
// //         val authorities = mutableSetOf<GrantedAuthority>()
// //         val token = userRequest.accessToken
// //         val payload = JWTPayload.interpret(token.tokenValue)
// //         for (authority in token.scopes) {
// //             authorities += SimpleGrantedAuthority("SCOPE_$authority")
// //         }
// //         for (authority in payload.authorities) {
// //             authorities += SimpleGrantedAuthority(authority)
// //         }
// //
// //         log.error("authUser : {}", payload.userName)
// //         return DefaultOAuth2User(
// //             authorities,
// //             mutableMapOf<String, Any>(
// //                 "clientId" to clientId,
// //                 "id" to payload.userName,
// //             ),
// //             "id",
// //         )
// //     }
// // }


