package com.github.flagshipio.jetbrain.toolWindow.flagsInFile

import com.github.flagshipio.jetbrain.dataClass.FlagAnalyzed

class FlagInFileNodeViewModel(
    val flagAnalyzed: FlagAnalyzed,
) {
    val flagKey = flagAnalyzed.flagKey
    val flagType = flagAnalyzed.flagType
    val flagDefaultValue = flagAnalyzed.flagDefaultValue
    val flagLineNumber = flagAnalyzed.lineNumber
}
