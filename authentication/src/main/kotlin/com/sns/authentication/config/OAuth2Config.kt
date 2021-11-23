package com.sns.authentication.config

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
    val passwordEncoder: PasswordEncoder
) : AuthorizationServerConfigurerAdapter() {

    override fun configure(clients: ClientDetailsServiceConfigurer?) {
        super.configure(clients)
        clients?.jdbc(dataSource)
            ?.passwordEncoder(passwordEncoder)
            ?.withClient("client")
            ?.secret("secret")
            ?.redirectUris("http://localhost:10001/api/v1/oauth/callback")
            ?.authorizedGrantTypes("auth_code")
            ?.scopes("read")
            ?.accessTokenValiditySeconds(300)
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer?) {
        super.configure(endpoints)
        endpoints?.accessTokenConverter(jwtAccessTokenConverter())
            ?.authenticationManager(authenticationManager)
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
