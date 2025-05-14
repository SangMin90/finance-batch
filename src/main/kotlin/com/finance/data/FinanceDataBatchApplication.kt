package com.finance.data

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class FinanceDataBatchApplication

fun main(args: Array<String>) {
    runApplication<FinanceDataBatchApplication>(*args)
}
