package com.finance.data.rest.coin.model

import com.fasterxml.jackson.annotation.JsonProperty

class CautionDto(
    @JsonProperty(value = "PRICE_FLUCTUATIONS")
    val priceFluctuations: Boolean,
    @JsonProperty(value = "TRADING_VOLUME_SOARING")
    val tradingVolumeSoaring: Boolean,
    @JsonProperty(value = "DEPOSIT_AMOUNT_SOARING")
    val depositAmountSoaring: Boolean,
    @JsonProperty(value = "GLOBAL_PRICE_DIFFERENCES")
    val globalPriceDifferences: Boolean,
    @JsonProperty(value = "CONCENTRATION_OF_SMALL_ACCOUNTS")
    val concentrationOfSmallAccounts: Boolean
)