package com.sns.user.core.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

@Configuration
class SwaggerConfiguration {
    @Bean
    fun api(): Docket =
        Docket(DocumentationType.OAS_30) // open api spec 3.0
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            .build()
            .apiInfo(
                ApiInfoBuilder()
                    .title("User API")
                    .description("User domain api")
                    .license("license")
                    .licenseUrl("https://github.com/juniors-dev-study/domain-driven-design")
                    .build()
            )
}
