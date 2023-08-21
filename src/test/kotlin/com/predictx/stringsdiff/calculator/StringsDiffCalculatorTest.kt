package com.predictx.stringsdiff.calculator

import com.predictx.stringsdiff.StringDiffVisualiserConfiguration.InputConfigProps
import com.predictx.stringsdiff.domain.StrCharInfo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.*

private const val T1_STRING_ID = "test1"
private const val T2_STRING_ID = "test2"

class StringsDiffCalculatorTest {

    private lateinit var stringsDiffCalculator: StrsDiffCalculator

    @Mock
    private val strCharInfoExtractor: StrCharInfoExtractor = mock(StrCharInfoExtractor::class.java)

    @BeforeEach
    fun setUp() {
        stringsDiffCalculator = StrsDiffCalculatorImpl(InputConfigProps(firstInputId = T1_STRING_ID, secondInputId = T2_STRING_ID), charsInfoExtractor = strCharInfoExtractor)
    }

    @Test
    fun computeDiff_SameCharacterInfo_expectNoMaxId() {
        val charInfoMapT1 = mapOf(Pair('a', StrCharInfo('a', 1, T1_STRING_ID)))
        val charInfoMapT2 = mapOf(Pair('a', StrCharInfo('a', 1, T2_STRING_ID)))

        doReturn(charInfoMapT1).`when`(strCharInfoExtractor).extract("a", T1_STRING_ID)
        doReturn(charInfoMapT2).`when`(strCharInfoExtractor).extract("a", T2_STRING_ID)

        val resultList = stringsDiffCalculator.computeDiff("a", "a")

        assertThat(resultList).containsExactly(StrCharInfo('a', 1, null))
    }

    @Test
    fun computeDiff_T2charCountIsBigger_expectT2MaxInfoOnly() {
        val charInfoMapT1 = mapOf(Pair('a', StrCharInfo('a', 1, T1_STRING_ID)))
        val charInfoMapT2 = mapOf(Pair('a', StrCharInfo('a', 2, T2_STRING_ID)))

        doReturn(charInfoMapT1).`when`(strCharInfoExtractor).extract("a", T1_STRING_ID)
        doReturn(charInfoMapT2).`when`(strCharInfoExtractor).extract("aa", T2_STRING_ID)

        val resultList = stringsDiffCalculator.computeDiff("a", "aa")

        assertThat(resultList).containsExactly(StrCharInfo('a', 2, T2_STRING_ID))
    }

    @Test
    fun computeDiff_checkDifferentCharsMutuallyExclusive_expectT1andT2MaxInfos() {
        val charInfoMapT1 = mapOf(Pair('a', StrCharInfo('a', 1, T1_STRING_ID)))
        val charInfoMapT2 = mapOf(Pair('b', StrCharInfo('b', 2, T2_STRING_ID)))

        doReturn(charInfoMapT1).`when`(strCharInfoExtractor).extract("a", T1_STRING_ID)
        doReturn(charInfoMapT2).`when`(strCharInfoExtractor).extract("bb", T2_STRING_ID)

        val resultList = stringsDiffCalculator.computeDiff("a", "bb")

        assertThat(resultList).containsExactly(StrCharInfo('a', 1, T1_STRING_ID), StrCharInfo('b', 2, T2_STRING_ID))
    }

    @Test
    fun computeDiff_checkTheFilterIsCalled(){

        val regex: Regex = mock(Regex::class.java)

        stringsDiffCalculator = StrsDiffCalculatorImpl(
            InputConfigProps(firstInputId = T1_STRING_ID, secondInputId = T2_STRING_ID),
            charsInfoExtractor = strCharInfoExtractor,
            regexFilterWrapper = Pair(true, regex)
        )

        stringsDiffCalculator.computeDiff("bla", "bla")

        verify(regex, times(2)).replace("bla","")
    }
}