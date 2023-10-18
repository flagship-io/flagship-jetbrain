package com.github.flagshipio.jetbrain.store

import com.github.flagshipio.jetbrain.cli.CliCommand
import com.github.flagshipio.jetbrain.dataClass.Flag
import com.github.flagshipio.jetbrain.services.FlagDataService
import com.intellij.openapi.project.Project

class FlagStore(project: Project) {

    private var flagDataService: FlagDataService
    private val cliCommand = CliCommand()

    init {
        flagDataService = project.getService(FlagDataService::class.java)
    }

    fun refreshFlag(): List<Flag>? {
        val flags = cliCommand.listFlagCli()
        if (flags != null) {
            flagDataService.loadState(flags)
        }
        return flags
    }

    fun saveFlag(flag: Flag): Flag? {
        val cliResponse = cliCommand.addFlagCli(flag)
        if (cliResponse != null) {
            flagDataService.saveFlag(cliResponse)
        }
        return cliResponse
    }

    fun editFlag(flag: Flag, newFlag: Flag): String? {
        val cliResponse = flag.id?.let { cliCommand.editFlagCli(it, newFlag) }
        if (cliResponse != null) {
            if (cliResponse.contains("edited successfully", true)) {
                flagDataService.editFlag(flag, newFlag)
            }
        }
        return cliResponse
    }

    fun deleteFlag(flag: Flag): String? {
        val cliResponse = flag.id?.let { cliCommand.deleteFlagCli(it) }
        if (cliResponse != null) {
            if (cliResponse.contains("deleted successfully", true)) {
                flagDataService.deleteFlag(flag)
            }
        }
        return cliResponse
    }

    fun getFlags(): List<Flag> {
        return flagDataService.state
    }
}