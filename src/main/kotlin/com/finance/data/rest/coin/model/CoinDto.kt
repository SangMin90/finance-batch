package com.finance.data.rest.coin.model

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CoinDto(
    val market: String
    , val koreanName: String
    , val englishName: String
    , val marketEvent: MarketEventDto
)
