package com.predictx.stringsdiff

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.EnabledIf
import org.springframework.test.context.junit.jupiter.SpringExtension

// in theory we could extend Regex class as @Component or extract it to its own config to avoid loading entire visualiser config
@ExtendWith(SpringExtension::class)
@ContextConfiguration(initializers = [ConfigDataApplicationContextInitializer::class], classes = [StringDiffVisualiserConfiguration::class])
class DefaultRegexTest {

    @Autowired
    var defaultInputRegexFilter: Regex? = null

    @EnabledIf("\${string-diff-visualiser.input.filter-regex-enabled}", loadContext = true)
    @Test
    @DisplayName("Checking regex conforming to original requirement of only lower case alphabet values are allowed")
    fun testDefaultRegex(){
        assertThat(defaultInputRegexFilter!!).matches {
            it.matches("?").and(it.matches("\n").and(it.matches("!"))).and(it.matches("A"))
                .and(it.matches("9")).and(it.matches(" ")).and(!it.matches("a"))
        }
    }

}