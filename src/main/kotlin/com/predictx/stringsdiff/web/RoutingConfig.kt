package com.predictx.stringsdiff.web

import com.predictx.stringsdiff.StrsDiffVisualiserServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse

private const val API_V_1_STRS_DIFF = "/v1/stringsdiff"

@Configuration
class RoutingConfig {

    @Bean
    fun routes(strsDiffsReqHandler: StrsDiffsReqHandler): RouterFunction<ServerResponse> {
        return RouterFunctions.route(RequestPredicates.POST(API_V_1_STRS_DIFF), strsDiffsReqHandler::handlePost)
    }

    @Bean
    fun strsDiffReqHandler(strsDiffVisualiser: StrsDiffVisualiserServiceImpl): StrsDiffsReqHandler{
        return StrsDiffsReqHandler(strsDiffVisualiser)
    }
}