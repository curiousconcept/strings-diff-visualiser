package com.predictx.stringsdiff

import com.predictx.stringsdiff.StringDiffVisualiserConfiguration.*
import com.predictx.stringsdiff.calculator.StrCharInfoExtractor
import com.predictx.stringsdiff.calculator.StrCharInfoExtractorImpl
import com.predictx.stringsdiff.calculator.StrsDiffCalculator
import com.predictx.stringsdiff.calculator.StrsDiffCalculatorImpl
import com.predictx.stringsdiff.display.StrsDiffResultDisplay
import com.predictx.stringsdiff.display.StrsDiffResultDisplayImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(value = [InputConfigProps::class])
class StringDiffVisualiserConfiguration {

    @Autowired
    lateinit var stringDiffVisualiserConfigProps: InputConfigProps

    @Bean
    @ConditionalOnProperty(prefix = "string-diff-visualiser.input", name = ["filter-regex-enabled"])
    fun defaultInputRegexFilter(): Regex {
        return Regex(stringDiffVisualiserConfigProps.filterRegex!!)
    }

    @Bean
    fun strCharInfoExtractor(configProps: InputConfigProps): StrCharInfoExtractor {
        return configProps.minCharCount?.let { StrCharInfoExtractorImpl(it) } ?: StrCharInfoExtractorImpl()
    }

    @Bean
    fun strsDiffCalculator(configProps: InputConfigProps,
                           strCharInfoExtractor: StrCharInfoExtractor,
                           defaultRegexFilter: Regex): StrsDiffCalculator {
        return StrsDiffCalculatorImpl(configProps, Pair(configProps.filterRegexEnabled, defaultRegexFilter), strCharInfoExtractor)
    }

    @Bean
    fun strsDiffDisplay(@Value("\${string-diff-visualiser.output.char-info-separator}") separator: String): StrsDiffResultDisplay {
        return StrsDiffResultDisplayImpl(separator)
    }

    @Bean
    fun strsDiffVisualiser(strsDiffCalculator: StrsDiffCalculator, strsResDisplay: StrsDiffResultDisplay): StrsDiffVisualiserService {
        return StrsDiffVisualiserServiceImpl(strsDiffCalculator, strsResDisplay)
    }

    @ConfigurationProperties("string-diff-visualiser.input")
    data class InputConfigProps(val filterRegex: String? = null,
                                val filterRegexEnabled: Boolean = false,
                                val minCharCount: Int? = null,
                                val firstInputId: String,
                                val secondInputId: String)
}