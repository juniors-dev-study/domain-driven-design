package com.sns.authentication.config

import javax.sql.DataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.io.ClassPathResource
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.datasource.init.DataSourceInitializer
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator

@Configuration
@EnableJdbcRepositories(basePackages = ["com.sns.authentication.user"])
class UserDataSourceConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.user-datasource")
    fun userDataSource(): DataSource = DataSourceBuilder.create().build()

    @Bean
    @Primary
    fun jdbcOperations(@Qualifier("userDataSource") datasource: DataSource): NamedParameterJdbcOperations? {
        return NamedParameterJdbcTemplate(datasource)
    }
}

@Configuration
@EnableJdbcRepositories("com.sns.authentication.config")
class AuthDataSourceConfig : AbstractJdbcConfiguration() {
    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.auth-datasource")
    fun authDataSource(): DataSource = DataSourceBuilder.create().build()

    @Bean
    fun dataSourceInitializer(@Qualifier("authDataSource") datasource: DataSource): DataSourceInitializer? {
        val resourceDatabasePopulator = ResourceDatabasePopulator()
        resourceDatabasePopulator.addScript(ClassPathResource("authentication_schema.sql"))
        val dataSourceInitializer = DataSourceInitializer()
        dataSourceInitializer.setDataSource(datasource)
        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator)
        return dataSourceInitializer
    }
}


