package com.github.flagshipio.jetbrain.store

import com.github.flagshipio.jetbrain.cli.CliCommand
import com.github.flagshipio.jetbrain.dataClass.FileAnalyzed
import com.github.flagshipio.jetbrain.services.FileDataService
import com.intellij.openapi.project.Project

class FlagsInFileStore(project: Project) {

    private var fileDataService: FileDataService
    private val cliCommand = CliCommand()

    init {
        fileDataService = project.getService(FileDataService::class.java)
    }

    fun refreshFlagInFile(path: String): List<FileAnalyzed>? {
        val files = cliCommand.listAnalyzedFlag(path)
        if (files != null) {
            fileDataService.loadState(files)
        }
        return files
    }

    fun getFiles(): List<FileAnalyzed> {
        return fileDataService.state
    }
}