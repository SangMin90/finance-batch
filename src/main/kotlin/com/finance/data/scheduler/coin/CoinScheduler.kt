package com.finance.data.scheduler.coin

import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.configuration.JobRegistry
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Scheduled
import java.time.LocalDateTime

@Configuration
class CoinScheduler(
    val jobLauncher: JobLauncher
    , val jobRegistry: JobRegistry
) {
    @Scheduled(cron = "50 * * * * *", zone = "Asia/Seoul")
    fun runCoinJob() {

        val jobParameters = JobParametersBuilder()
            .addLocalDateTime("date", LocalDateTime.now())
            .toJobParameters()

        jobLauncher.run(jobRegistry.getJob("coinJob"), jobParameters)
    }
}