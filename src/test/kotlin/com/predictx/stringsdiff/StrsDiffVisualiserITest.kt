package com.predictx.stringsdiff

import com.predictx.*
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@ContextConfiguration(initializers = [ConfigDataApplicationContextInitializer::class], classes = [StringDiffVisualiserConfiguration::class])
class StrsDiffVisualiserITest {

    @Autowired
    private lateinit var strsDiffVisualiser: StrsDiffVisualiserServiceImpl

    @ParameterizedTest
    @CsvSource(
        "$example1_s1,                      $example1_s2,               $example1_output",
        "$example2_s1,                      $example2_s2,               $example2_output",
//      "$example3_s1,                      $example3_s2,               $example3_output
    )
    fun visualise(s1: String, s2: String, expectedOutput: String) {
        assertThat(strsDiffVisualiser.visualise(s1, s2)).isEqualTo(expectedOutput)
    }
}