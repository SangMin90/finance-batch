package com.finance.data.tasklet.coin

import com.finance.data.domain.coin.`object`.Coin
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component

@Component
class CoinTasklet(
    private val financeDatabase: Database,
): Tasklet {
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        transaction(financeDatabase) {
            Coin.deleteAll()
        }

        return RepeatStatus.FINISHED
    }
}