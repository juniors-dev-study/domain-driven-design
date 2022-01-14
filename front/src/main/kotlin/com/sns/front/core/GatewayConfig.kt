package com.sns.front.core

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.gateway.filter.factory.TokenRelayGatewayFilterFactory
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author Hyounglin Jun
 */
@Configuration
class GatewayConfig {
    @Autowired
    lateinit var filterFactory: TokenRelayGatewayFilterFactory  // token 넣어주는 필터

    @Bean
    fun myRoutes(builder: RouteLocatorBuilder): RouteLocator? {
        return builder.routes()
            .route { p ->
                p
                    .path("/article-api/**")
                    .filters { filter ->
                        filter
                            .filter(filterFactory.apply())
                            .rewritePath("/article-api", "/api")
                    }
                    .uri("http://localhost:10002")
            }
            .route { p ->
                p
                    .path("/user-api/**")
                    .filters { filter ->
                        filter
                            .filter(filterFactory.apply())
                            .rewritePath("/user-api", "/api")
                    }
                    .uri("http://localhost:10001")
            }

            .build()
    }
}
