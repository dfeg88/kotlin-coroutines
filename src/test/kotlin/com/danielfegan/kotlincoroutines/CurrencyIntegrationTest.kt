package com.danielfegan.kotlincoroutines

import com.github.tomakehurst.wiremock.client.WireMock.*
import io.netty.handler.codec.http.HttpHeaderValues
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.test.web.reactive.server.expectBody
import java.nio.file.Files
import java.time.Instant

class CurrencyIntegrationTest : BaseIntegrationTest() {

    @AfterEach
    fun tearDown() {
        reset()
    }

    @Test
    fun `two async calls only take as long as the longest call`() {
        stubApiRequests()

        val start = Instant.now()

        webTestClient.get()
            .uri { it.host("http://localhost").port(port).path("api/v1/currency").build() }
            .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON.toString())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBody<String>().isEqualTo("0.6666666666666666")

        val end = Instant.now()

        val timeTaken = end.minusSeconds(start.epochSecond)

        Assertions.assertTrue(timeTaken.epochSecond == 5L)
    }

    private fun stubApiRequests() {
        stubFor(
            get(urlEqualTo("/api/currencyone"))
                .withHeader(CONTENT_TYPE, equalTo(APPLICATION_JSON_VALUE))
                .willReturn(
                    aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBody(Files.readString(currencyOneResponse.file.toPath()))
                )
        )

        stubFor(
            get(urlEqualTo("/api/currencytwo"))
                .withHeader(CONTENT_TYPE, equalTo(APPLICATION_JSON_VALUE))
                .willReturn(
                    aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBody(Files.readString(currencyTwoResponse.file.toPath()))
                )
        )
    }

}