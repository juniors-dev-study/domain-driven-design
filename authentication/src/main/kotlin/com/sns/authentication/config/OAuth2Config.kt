package com.sns.authentication.config

import com.sns.authentication.LoginUserService
import javax.sql.DataSource
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter

@EnableAuthorizationServer
@Configuration
class OAuth2Config(
    val resourceServerProperties: ResourceServerProperties,
    val authenticationManager: AuthenticationManager,
    val dataSource: DataSource,
    val passwordEncoder: PasswordEncoder,
    val loginUserService: LoginUserService
) : AuthorizationServerConfigurerAdapter() {

    override fun configure(clients: ClientDetailsServiceConfigurer?) {
        super.configure(clients)
        clients?.jdbc(dataSource)
            ?.passwordEncoder(passwordEncoder)
            ?.withClient("front_client")
            ?.secret("secret")
            ?.redirectUris("http://local-front.ddd.sns.com:10100/login/oauth2/code/auth_server")  // default redirect
            ?.authorizedGrantTypes("authorization_code")
            ?.scopes("read")
            ?.autoApprove(true)
            ?.accessTokenValiditySeconds(300)
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer?) {
        super.configure(endpoints)
        endpoints?.accessTokenConverter(jwtAccessTokenConverter())
            ?.authenticationManager(authenticationManager)
            ?.userDetailsService(loginUserService)
    }

    override fun configure(security: AuthorizationServerSecurityConfigurer?) {
        super.configure(security)
    }

    @Bean
    fun jwtAccessTokenConverter(): JwtAccessTokenConverter? {
        // JWT key-value 방식을 사용하기 위한 설정입니다.
        val accessTokenConverter = JwtAccessTokenConverter()
        accessTokenConverter.setSigningKey(resourceServerProperties.jwt.keyValue)
        return accessTokenConverter
    }
}
