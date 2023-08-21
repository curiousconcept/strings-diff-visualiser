package com.predictx.stringsdiff.calculator

import com.predictx.stringsdiff.domain.StrCharInfo

interface StrCharInfoExtractor {
    fun extract(inputString: String, inputStringId: String? = inputString): Map<Char, StrCharInfo>
}