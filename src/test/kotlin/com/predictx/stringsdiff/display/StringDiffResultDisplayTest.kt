package com.predictx.stringsdiff.display

import com.predictx.stringsdiff.domain.StrCharInfo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StringDiffResultDisplayTest {

    private val stringDiffResultDisplay = StrsDiffResultDisplayImpl("/")

    @Test
    fun displayStringDiffResult() {

        val charInfoList = listOf(
            StrCharInfo('z', 3, "test1"), StrCharInfo('b', 2, "test2"),
            StrCharInfo('a', 2)
        )

        val result = stringDiffResultDisplay.displayStringDiffResult(charInfoList)

        assertThat(result).isEqualTo("test1:zzz/test2:bb/=:aa")
    }
}