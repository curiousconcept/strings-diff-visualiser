package com.predictx.stringsdiff.display

import com.predictx.stringsdiff.domain.StrCharInfo

interface StrsDiffResultDisplay {
    fun displayStringDiffResult(chars: List<StrCharInfo>): String
}