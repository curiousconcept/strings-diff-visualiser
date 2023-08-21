package com.predictx.stringsdiff.calculator

import com.predictx.stringsdiff.domain.StrCharInfo

class StrCharInfoExtractorImpl(private val minCountFilter: Int = 0) : StrCharInfoExtractor {

    override fun extract(inputString: String, inputStringId: String?): Map<Char, StrCharInfo> {
        return inputString.toCharArray().asSequence().constrainOnce()
            .groupBy { it }
            .mapValues { StrCharInfo(it.key, it.value.count(), inputStringId) }
            .filterValues { it.count >= minCountFilter }
    }
}