package com.predictx.stringsdiff.calculator

import com.predictx.stringsdiff.domain.StrCharInfo

interface StrsDiffCalculator {
    fun computeDiff(s1: String, s2: String): List<StrCharInfo>
}