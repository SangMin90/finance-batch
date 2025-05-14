package com.finance.data.domain.coin.`object`

import com.finance.data.common.CoinWarning
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object CoinCautionHistory: LongIdTable("TH_COIN_CAUTION") {
    val market = varchar("market", 255)
    val cautionType = enumerationByName("caution_type", 255, CoinWarning::class)
    val cautionYn = char("caution_yn", 1)
    val deleteYn = char("delete_yn", 1).databaseGenerated()
    val createdBy = varchar("created_by", 255).databaseGenerated()
    val createdAt = datetime("created_at").clientDefault { LocalDateTime.now() }
    val updatedBy = varchar("updated_by", 255).databaseGenerated()
    val updatedAt = datetime("updated_at").clientDefault { LocalDateTime.now() }
}