package com.finance.data

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FinanceDataBatchApplication

fun main(args: Array<String>) {
    runApplication<FinanceDataBatchApplication>(*args)
}
