package com.finance.data.config.datasource

import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
class FinanceDataSourceConfig(
    @Value("\${spring.datasource.finance.jdbc-url}") val url: String,
    @Value("\${spring.datasource.finance.username}") val username: String,
    @Value("\${spring.datasource.finance.password}") val password: String,
    @Value("\${spring.datasource.finance.driver-class-name}") val driver: String,
) {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.finance")
    fun financeDataSource(): DataSource {
        return DataSourceBuilder.create()
            .type(HikariDataSource::class.java)
            .build()
    }

    @Bean
    @Qualifier("financeTransactionManager")
    fun financeTransactionManager(): PlatformTransactionManager {
        return DataSourceTransactionManager(financeDataSource())
    }


    @Bean
    fun financeDatabase(): Database {
        return Database.connect(
            url = url,
            driver = driver,
            user = username,
            password = password
        )
    }
}