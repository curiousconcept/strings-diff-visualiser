package com.predictx.stringsdiff.web

import com.predictx.stringsdiff.StrsDiffVisualiserServiceImpl
import org.junit.jupiter.api.Test
import org.mockito.Mockito.doReturn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters

@WebFluxTest
@ContextConfiguration(classes = [RoutingConfig::class])
class StrsDiffsReqHandlerTest{

    @Autowired
    lateinit var webClient: WebTestClient

    @Autowired
    lateinit var strsDiffsReqHandler: StrsDiffsReqHandler

    @MockBean
    lateinit var strsDiffVisualiser: StrsDiffVisualiserServiceImpl

    @Test
    fun handlePost() {

        doReturn("test").`when`(strsDiffVisualiser).visualise("hello", "world")

        val strsDiffResources = StrsDiffResource("hello", "world")

        webClient.post().uri { uriBuilder -> uriBuilder.path("/v1/stringsdiff").build() }
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(strsDiffResources))
            .exchange().expectStatus().isEqualTo(HttpStatusCode.valueOf(201)).expectBody()
            .jsonPath("$.input_one").isEqualTo("hello")
            .jsonPath("$.input_two").isEqualTo("world")
            .jsonPath("$.diff_output").isEqualTo("test")

    }
}