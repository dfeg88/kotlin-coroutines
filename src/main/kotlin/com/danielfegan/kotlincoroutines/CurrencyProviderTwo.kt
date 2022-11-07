package com.danielfegan.kotlincoroutines

import com.danielfegan.kotlincoroutines.model.CurrencyResponse
import io.netty.handler.codec.http.HttpHeaderValues
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchange
import java.net.URI

@Component
class CurrencyProviderTwo(private val webClient: WebClient) {

    @Value("\${api.currency-two.host}")
    private lateinit var currencyHostTwo: String

    suspend fun get(): CurrencyResponse {
        withContext(Dispatchers.IO) {
            Thread.sleep(3000)
        }
        return webClient.get()
            .uri { URI.create(currencyHostTwo) }
            .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON.toString())
            .accept(MediaType.APPLICATION_JSON)
            .awaitExchange { it.awaitBody() }
    }

}