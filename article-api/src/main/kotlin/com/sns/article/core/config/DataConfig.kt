package com.sns.article.core.config

/**
 * @author Hyounglin Jun
 */
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import javax.sql.DataSource

@Configuration
class DataConfig {
    @Bean
    fun transactionManager(dataSource: DataSource?): DataSourceTransactionManager? {
        val dataSourceTransactionManager = DataSourceTransactionManager()
        dataSourceTransactionManager.dataSource = dataSource
        return dataSourceTransactionManager
    }
}
