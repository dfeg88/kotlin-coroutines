package com.danielfegan.kotlincoroutines

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/currency")
class CurrencyController(private val currencyService: CurrencyService) {

    @GetMapping
    suspend fun getExchangeRates(): ResponseEntity<String> = ResponseEntity.ok(currencyService.getBestExchangeRate())

}