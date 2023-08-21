package com.predictx.stringsdiff.web

// reuse the same resource for API input and output for convenience.
data class StrsDiffResource(val inputOne: String, val inputTwo: String, val diffOutput: String? = null)