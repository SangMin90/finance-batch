package com.finance.data.batch.coin

import com.finance.data.common.CoinWarning.NONE
import com.finance.data.domain.coin.`object`.Coin
import com.finance.data.domain.coin.`object`.CoinCautionHistory
import com.finance.data.reader.coin.CoinItemReader
import com.finance.data.rest.coin.model.CoinDto
import com.finance.data.rest.coin.service.CoinInterface
import com.finance.data.tasklet.coin.CoinTasklet
import lombok.RequiredArgsConstructor
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.support.CompositeItemWriter
import org.springframework.batch.item.support.builder.CompositeItemWriterBuilder
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@RequiredArgsConstructor
@Configuration
class CoinBatch(
    val jobRepository: JobRepository
    , @Qualifier("financeTransactionManager") val platformTransactionManager: PlatformTransactionManager
    , val financeDatabase: Database
    , val coinService: CoinInterface
) {

    @Bean
    fun coinJob(): Job {
        return JobBuilder("coinJob", jobRepository)
            .start(coinDeleteAllStep())
            .next(coinStep())
            .build()
    }

    @Bean
    fun coinDeleteAllStep(): Step {
        return StepBuilder("coinDeleteAllStep", jobRepository)
            .tasklet(coinDeleteAllTasklet(), platformTransactionManager)
            .build()
    }

    @Bean
    fun coinDeleteAllTasklet(): Tasklet {
        return CoinTasklet(financeDatabase)
    }

    @Bean
    fun coinStep(): Step {
        return StepBuilder("coinStep", jobRepository)
            .chunk<CoinDto, CoinDto>(100, platformTransactionManager)
            .reader(coinItemReader())
            .writer(coinCompositeWriter())
            .build()
    }

    @Bean
    fun coinItemReader(): ItemReader<CoinDto> {
        return CoinItemReader(coinService)
    }

    @Bean
    fun coinCompositeWriter(): CompositeItemWriter<CoinDto> {
        return CompositeItemWriterBuilder<CoinDto>()
            .delegates(listOf(coinItemWriter, coinCautionItemWriter))
            .build()
    }

    private val coinItemWriter: ItemWriter<CoinDto> = ItemWriter { coins ->
        transaction(financeDatabase) {
            Coin.batchInsert(
                data = coins,
                shouldReturnGeneratedValues = false
            ) { dto ->
                this[Coin.market] = dto.market
                this[Coin.engName] = dto.koreanName
                this[Coin.korName] = dto.englishName
                val cautionType = dto.marketEvent.getCautionType()
                this[Coin.cautionType] = cautionType
                this[Coin.cautionYn] = if (NONE == cautionType) "N" else "Y"
            }
        }
    }

    private val coinCautionItemWriter: ItemWriter<CoinDto> = ItemWriter { coins ->
        transaction(financeDatabase) {
            CoinCautionHistory.batchInsert(
                data = coins,
                shouldReturnGeneratedValues = false
            ) { dto ->
                this[CoinCautionHistory.market] = dto.market
                val cautionType = dto.marketEvent.getCautionType()
                this[CoinCautionHistory.cautionType] = cautionType
                this[CoinCautionHistory.cautionYn] = if (NONE == cautionType) "N" else "Y"
            }
        }
    }
}