package com.predictx.stringsdiff

import com.predictx.stringsdiff.calculator.StrsDiffCalculator
import com.predictx.stringsdiff.display.StrsDiffResultDisplay
import com.predictx.stringsdiff.domain.StrCharInfo
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class StrsDiffVisualiserTest {

    @Mock
    private lateinit var strsDiffResultDisplay: StrsDiffResultDisplay

    @Mock
    private lateinit var strsDiffCalculator: StrsDiffCalculator

    @InjectMocks
    private lateinit var strsDiffVisualiser: StrsDiffVisualiserServiceImpl

    @Test
    fun checkCalculatorAndDisplayAreCalled() {
        val strsCharInfos = listOf(StrCharInfo('a', 1, "1"), StrCharInfo('b', 1, "2"))
        doReturn(strsCharInfos).`when`(strsDiffCalculator).computeDiff("a","b")

        strsDiffVisualiser.visualise("a", "b")

        verify(strsDiffResultDisplay, only()).displayStringDiffResult(strsCharInfos)
    }
}