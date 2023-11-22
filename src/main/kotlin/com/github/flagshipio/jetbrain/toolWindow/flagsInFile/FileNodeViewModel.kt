package com.github.flagshipio.jetbrain.toolWindow.flagsInFile

import com.github.flagshipio.jetbrain.dataClass.FileAnalyzed

class FileNodeViewModel(
    val file: FileAnalyzed,
) {
    var fileName = file.file
    var flagInFile = file.results
}
