package com.predictx.stringsdiff.web

import com.predictx.stringsdiff.StrsDiffVisualiserService
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

class StrsDiffsReqHandler(private val strsDiffVisualiser: StrsDiffVisualiserService) {
    fun handlePost(request: ServerRequest): Mono<ServerResponse> {
        return request.bodyToMono(StrsDiffResource::class.java)
            .flatMap { sdr ->
                Mono.fromSupplier { strsDiffVisualiser.visualise(sdr.inputOne, sdr.inputTwo) }.map { StrsDiffResource(sdr.inputOne, sdr.inputTwo, it) }
            }
            .flatMap { sdr ->
                ServerResponse.status(HttpStatus.CREATED) // ideally we would have location for this resource representing this unique comparison
                              .body(BodyInserters.fromValue(sdr))
            }
    }
}