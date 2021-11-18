package com.sns.user.core.config

import javax.sql.DataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DataSourceTransactionManager

@Configuration
class DataConfig {
    @Bean
    fun transactionManager(dataSource: DataSource?): DataSourceTransactionManager? {
        val dataSourceTransactionManager = DataSourceTransactionManager()
        dataSourceTransactionManager.dataSource = dataSource
        return dataSourceTransactionManager
    }
}
