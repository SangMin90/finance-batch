package com.finance.data.rest.coin.service

import com.finance.data.rest.coin.model.CoinDto

interface CoinInterface {
    fun getCoinInfo(): MutableList<CoinDto>
}