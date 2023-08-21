package com.predictx.stringsdiff.calculator

import com.predictx.stringsdiff.domain.StrCharInfo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class StrCharInfoExtractorTest {

    private var strCharInfoExtractor: StrCharInfoExtractor = StrCharInfoExtractorImpl()

    @Test
    fun extractCharInfoOfStringA() {
        val charInfos = strCharInfoExtractor.extract("a")
        val expectedCharInfo = StrCharInfo('a', 1, "a")
        assertThat(charInfos).hasSize(1).hasEntrySatisfying('a') { assertEquals(expectedCharInfo, it) }
    }

    @Test
    fun extractCharInfosInHello() {
        val id = "Hello"
        val expectedHelloCharCounts = mapOf(
            Pair('H', StrCharInfo('H', 1, id)),
            Pair('e', StrCharInfo('e', 1, id)),
            Pair('l', StrCharInfo('l', 2, id)),
            Pair('o', StrCharInfo('o', 1, id))
        )

        val charFreqs = strCharInfoExtractor.extract(id)

        assertThat(charFreqs).containsExactlyEntriesOf(expectedHelloCharCounts)
    }

    @Test
    fun extractCharInfosWithFilterApplied(){

        strCharInfoExtractor = StrCharInfoExtractorImpl(2)

        val expectedHelloCharCounts = mapOf(Pair('l', StrCharInfo('l',2, "Hello")))

        val freqRes = strCharInfoExtractor.extract("Hello")

        assertThat(freqRes).containsExactlyEntriesOf(expectedHelloCharCounts)
    }

    @Test
    fun charIdIsSetToSpecified(){
        val charInfos = strCharInfoExtractor.extract("a", "Specified")
        val expectedCharInfo = StrCharInfo('a', 1, "Specified")
        assertThat(charInfos).hasSize(1).hasEntrySatisfying('a') { assertEquals(expectedCharInfo, it) }
    }

}