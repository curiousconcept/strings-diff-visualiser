package com.predictx.stringsdiff.calculator

import com.predictx.stringsdiff.StringDiffVisualiserConfiguration.InputConfigProps
import com.predictx.stringsdiff.domain.StrCharInfo

class StrsDiffCalculatorImpl (private val configProps: InputConfigProps,
                              private val regexFilterWrapper: Pair<Boolean, Regex?> = Pair(false,null),
                              private val charsInfoExtractor: StrCharInfoExtractor) : StrsDiffCalculator {

    override fun computeDiff(s1: String, s2: String) : List<StrCharInfo> {

        val s1CharsInfo = getCharsInfo(s1, configProps.firstInputId)
        val s2CharsInfo = getCharsInfo(s2, configProps.secondInputId)

        return extractMaxCharInfos(s1CharsInfo, s2CharsInfo)
    }

    private fun extractMaxCharInfos(s1CharsInfoMap: Map<Char, StrCharInfo>, s2CharsInfoMap: Map<Char, StrCharInfo>) : List<StrCharInfo> {

        val charsKeys = extractAllCharKeys(s1CharsInfoMap, s2CharsInfoMap)
        val maxCharInfos: MutableList<StrCharInfo> = mutableListOf()

        for (charKey in charsKeys) {

            val s1CharInfo = s1CharsInfoMap[charKey] ?: StrCharInfo(char = charKey, count = 0)
            val s2CharInfo = s2CharsInfoMap[charKey] ?: StrCharInfo(char = charKey, count = 0)

            val charInfoWithMaxCount = getCharInfoWithHighestCount(s1CharInfo, s2CharInfo)

            charInfoWithMaxCount?.let{ maxCharInfos.add(it) }
        }

        return maxCharInfos
    }

    private fun getCharInfoWithHighestCount(s1CharInfo: StrCharInfo, s2CharInfo: StrCharInfo): StrCharInfo? {
        return when {
            s1CharInfo.count == 0 && s2CharInfo.count == 0 -> null
            s1CharInfo.count > s2CharInfo.count -> s1CharInfo
            s2CharInfo.count > s1CharInfo.count -> s2CharInfo
            else -> StrCharInfo(s1CharInfo.char, s1CharInfo.count)
        }
    }

    private fun extractAllCharKeys(s1CharsCountMap: Map<Char, StrCharInfo>, s2CharsCountMap: Map<Char, StrCharInfo>) : Set<Char> {
        val uniqueCharKeys = s1CharsCountMap.keys.toMutableSet()
        uniqueCharKeys.addAll(s2CharsCountMap.keys)
        return uniqueCharKeys;
    }

    private fun getCharsInfo(input: String, stringId: String): Map<Char, StrCharInfo> {
        val regex = regexFilterWrapper.second
        val filteredOutput = if (regexFilterWrapper.first && regex !=null) regex.replace(input, "") else input
        return charsInfoExtractor.extract(filteredOutput, stringId)
    }

}