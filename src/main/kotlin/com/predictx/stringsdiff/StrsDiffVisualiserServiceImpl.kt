package com.predictx.stringsdiff

import com.predictx.stringsdiff.calculator.StrsDiffCalculator
import com.predictx.stringsdiff.display.StrsDiffResultDisplay
import com.predictx.stringsdiff.display.StrsDiffResultDisplayImpl

class StrsDiffVisualiserServiceImpl(private val strsDiffCalculator: StrsDiffCalculator,
                                    private val strsDiffResultDisplay: StrsDiffResultDisplay) : StrsDiffVisualiserService {
    override fun visualise(firstInput: String, secondInput: String) : String {
        return strsDiffResultDisplay.displayStringDiffResult(strsDiffCalculator.computeDiff(firstInput, secondInput))
    }
}