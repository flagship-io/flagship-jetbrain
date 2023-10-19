package com.github.flagshipio.jetbrain.store

import com.github.flagshipio.jetbrain.cli.CliCommand
import com.github.flagshipio.jetbrain.dataClass.Flag
import com.github.flagshipio.jetbrain.services.FlagDataService
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages

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
            Messages.showMessageDialog("Feature Flag saved", "Status", Messages.getInformationIcon())
        }
        return cliResponse
    }

    fun editFlag(flag: Flag, newFlag: Flag): Flag? {
        val cliResponse = flag.id?.let { cliCommand.editFlagCli(it, newFlag) }
        if (cliResponse != null) {
            flagDataService.editFlag(flag, cliResponse)
            Messages.showMessageDialog("Feature Flag edited", "Status", Messages.getInformationIcon())
        }
        return cliResponse
    }

    fun deleteFlag(flag: Flag): String? {
        val cliResponse = flag.id?.let { cliCommand.deleteFlagCli(it) }
        if (cliResponse != null) {
            if (cliResponse.contains("deleted", true)) {
                flagDataService.deleteFlag(flag)
                Messages.showMessageDialog("Feature Flag deleted", "Status", Messages.getInformationIcon())
            }
        }
        return cliResponse
    }

    fun getFlags(): List<Flag> {
        return flagDataService.state
    }
}