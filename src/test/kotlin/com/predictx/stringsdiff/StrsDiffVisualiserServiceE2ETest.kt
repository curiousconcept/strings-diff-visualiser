package com.predictx.stringsdiff

import com.predictx.example1_output
import com.predictx.example1_s1
import com.predictx.example1_s2
import com.predictx.example2_s1
import com.predictx.stringsdiff.web.StrsDiffResource
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StrsDiffVisualiserServiceE2ETest {

	@Autowired
	lateinit var webClient: WebTestClient

	@Test
	fun handlePost() {
		val strsDiffResources = StrsDiffResource(example1_s1, example1_s2)

		webClient.post().uri { uriBuilder -> uriBuilder.path("/v1/stringsdiff").build() }
			.contentType(MediaType.APPLICATION_JSON)
			.body(BodyInserters.fromValue(strsDiffResources))
			.exchange().expectStatus().isEqualTo(HttpStatusCode.valueOf(201)).expectBody()
			.jsonPath("$.input_one").isEqualTo(example1_s1)
			.jsonPath("$.input_two").isEqualTo(example1_s2)
			.jsonPath("$.diff_output").isEqualTo(example1_output)

	}
}
