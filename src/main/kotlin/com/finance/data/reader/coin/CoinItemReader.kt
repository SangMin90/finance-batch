package com.finance.data.reader.coin

import com.finance.data.rest.coin.model.CoinDto
import com.finance.data.rest.coin.service.CoinInterface
import org.springframework.batch.item.ItemReader

class CoinItemReader(
    private val coinService: CoinInterface
    , private var coins: MutableList<CoinDto> = mutableListOf()
): ItemReader<CoinDto> {

    private var currIndex: Int = 0;

    override fun read(): CoinDto? {

        if (coins.isEmpty()) {
            if (currIndex == 0) {
                coins = coinService.getCoinInfo()
            } else {
                currIndex = 0
            }
        }

        if (coins.isNotEmpty()) {
            currIndex++;
            return coins.removeAt(0)
        }

        return null
    }
}