package com.sns.authentication.config

import com.sns.authentication.LoginUserService
import com.sns.authentication.LoginUserTokenEnhancer
import javax.sql.DataSource
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.io.FileSystemResource
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory

@EnableAuthorizationServer
@Configuration
class OAuth2Config(
    val resourceServerProperties: ResourceServerProperties,
    val authenticationManager: AuthenticationManager,
    val dataSource: DataSource,
    val passwordEncoder: PasswordEncoder,
    val loginUserService: LoginUserService,
    val jwtAccessTokenConverter: JwtAccessTokenConverter,
    val loginUserTokenEnhancer: LoginUserTokenEnhancer
) : AuthorizationServerConfigurerAdapter() {

    override fun configure(clients: ClientDetailsServiceConfigurer?) {
        super.configure(clients)
        clients?.jdbc(dataSource)
            ?.passwordEncoder(passwordEncoder)
            ?.withClient("front_client")
            ?.secret("secret")
            ?.redirectUris(
                "http://local-front.ddd.sns.com:10100/login/oauth2/code/auth_server",
                "http://local-front.ddd.sns.com:10100/home",
            )  // default redirect
            ?.authorizedGrantTypes("authorization_code", "refresh_token")
            ?.scopes("read")
            ?.autoApprove(true)
            ?.accessTokenValiditySeconds(300)
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer?) {
        super.configure(endpoints)
        val tokenEnhancerChain = TokenEnhancerChain()
        tokenEnhancerChain.setTokenEnhancers(listOf(loginUserTokenEnhancer, jwtAccessTokenConverter))

        endpoints!!.tokenEnhancer(tokenEnhancerChain)
            .accessTokenConverter(jwtAccessTokenConverter)
            .authenticationManager(authenticationManager)
            .userDetailsService(loginUserService)
    }

    override fun configure(security: AuthorizationServerSecurityConfigurer?) {
        super.configure(security)
    }
}

@Configuration
class OAuth2ConfigBean {
    @Bean
    @Profile("test")
    fun jwtAccessTokenConverterTest(): JwtAccessTokenConverter {
        val accessTokenConverter = JwtAccessTokenConverter()
        accessTokenConverter.setSigningKey("jwtKey")
        return accessTokenConverter
    }

    @Bean
    @ConditionalOnMissingBean
    fun jwtAccessTokenConverter(
        @Value("\${security.oauth2.resource.jwt.custom-jks-path}") keyPath: String,
        @Value("\${security.oauth2.resource.jwt.custom-jks-passwd}") passwd: String,
        @Value("\${security.oauth2.resource.jwt.custom-jks-alias}") alias: String
    ): JwtAccessTokenConverter {
        LoggerFactory.getLogger(this.javaClass).error("passwd : {}", passwd)
        val keyStoreKeyFactory = KeyStoreKeyFactory(FileSystemResource(keyPath), passwd.toCharArray())
        val accessTokenConverter = JwtAccessTokenConverter()
        // accessTokenConverter.setSigningKey(resourceServerProperties.jwt.keyValue)
        accessTokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair(alias))
        return accessTokenConverter
    }
}


