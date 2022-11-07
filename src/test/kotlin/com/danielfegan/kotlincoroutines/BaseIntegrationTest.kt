package com.danielfegan.kotlincoroutines

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.core.io.Resource
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
@AutoConfigureWebTestClient(timeout = "15000")
class BaseIntegrationTest() {

    @Autowired
    protected lateinit var webTestClient: WebTestClient

    @LocalServerPort
    protected var port: Int = 0

    @Value("classpath:currency-one-response.json")
    protected lateinit var currencyOneResponse: Resource

    @Value("classpath:currency-two-response.json")
    protected lateinit var currencyTwoResponse: Resource

    @Value("\${api.currency-one.host}")
    protected lateinit var currencyOneHost: String

    @Value("\${api.currency-two.host}")
    protected lateinit var currencyTwoHost: String

}