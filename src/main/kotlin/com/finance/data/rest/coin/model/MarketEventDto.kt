package com.finance.data.rest.coin.model

import com.finance.data.common.CoinWarning
import com.finance.data.common.CoinWarning.*

data class MarketEventDto(
    val warning: Boolean,
    val caution: CautionDto
) {
    fun getCautionType(): CoinWarning {
        return if (warning) {
            WARNING
        } else {
            if (caution.priceFluctuations) {
                PRICE_FLUCTUATIONS
            } else if (caution.depositAmountSoaring) {
                DEPOSIT_AMOUNT_SOARING
            } else if (caution.tradingVolumeSoaring) {
                TRADING_VOLUME_SOARING
            } else if (caution.concentrationOfSmallAccounts) {
                CONCENTRATION_OF_SMALL_ACCOUNTS
            } else if (caution.globalPriceDifferences) {
                GLOBAL_PRICE_DIFFERENCES
            } else {
                NONE
            }
        }
    }
}