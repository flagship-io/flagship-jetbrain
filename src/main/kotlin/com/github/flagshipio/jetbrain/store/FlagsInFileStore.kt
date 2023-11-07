package com.github.flagshipio.jetbrain.store

import com.github.flagshipio.jetbrain.cli.CliCommand
import com.github.flagshipio.jetbrain.dataClass.FileAnalyzed
import com.github.flagshipio.jetbrain.dataClass.Flag
import com.github.flagshipio.jetbrain.dataClass.FlagAnalyzed
import com.github.flagshipio.jetbrain.services.FlagDataService
import com.github.flagshipio.jetbrain.services.FlagsInFileDataService
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages

class FlagsInFileStore(project: Project) {

    private var flagsInFileDataService: FlagsInFileDataService
    private val cliCommand = CliCommand()

    init {
        flagsInFileDataService = project.getService(FlagsInFileDataService::class.java)
    }

    fun refreshFlag(path: String): List<FlagAnalyzed> {
        val flagResults: ArrayList<FlagAnalyzed> = ArrayList()
        val files = cliCommand.listAnalyzedFlag(path)
        files?.map { file ->
            file.results?.map { flag ->
                flag.flagFile = file.file
                flagResults.add(flag)
            }
        }
        println(files)
        if (files != null) {
            flagsInFileDataService.loadState(flagResults)
        }
        return flagResults
    }

    fun getFlags(): List<FlagAnalyzed> {
        return flagsInFileDataService.state
    }
}