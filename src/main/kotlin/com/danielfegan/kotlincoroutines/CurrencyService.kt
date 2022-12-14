package com.danielfegan.kotlincoroutines

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service

@Service
class CurrencyService(
    private val currencyProviderOne: CurrencyProviderOne,
    private val currencyProviderTwo: CurrencyProviderTwo
) {

    suspend fun getBestExchangeRate() = coroutineScope {
        val currencyOneResult = async { currencyProviderOne.get() }
        val currencyTwoResult = async { currencyProviderTwo.get() }

        listOf(currencyOneResult.await(), currencyTwoResult.await())
            .maxOf { it.usd / it.gbp }
    }

}