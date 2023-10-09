package com.github.flagshipio.jetbrain.store

import com.github.flagshipio.jetbrain.cli.CheckCLI
import com.github.flagshipio.jetbrain.dataClass.Flag
import com.github.flagshipio.jetbrain.services.FlagDataService
import com.intellij.openapi.project.Project

class FlagStore(project: Project) {

    private var flagDataService: FlagDataService
    private val checkCLI = CheckCLI()

    init {
        flagDataService = project.getService(FlagDataService::class.java)
    }

    fun refreshFlag(): List<Flag>? {
        val flags = checkCLI.listFlagCli()

        if (flags != null) {
            flagDataService.loadState(flags)
        }
        return flags

    }

    fun getFlag(): List<Flag> {
        return flagDataService.state
    }
}