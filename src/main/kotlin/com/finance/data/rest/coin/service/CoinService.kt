package com.finance.data.rest.coin.service

import com.finance.data.rest.coin.model.CoinDto
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient

@Service
class CoinService: CoinInterface {
    override fun getCoinInfo(): MutableList<CoinDto> {
        val restClient = RestClient.create()
        return restClient.get()
            .uri("https://api.upbit.com/v1/market/all?is_details=true")
            .retrieve()
            .body(object : ParameterizedTypeReference<MutableList<CoinDto>>() {}) ?: mutableListOf()
    }
}