package com.github.flagshipio.jetbrain.store

import com.github.flagshipio.jetbrain.cli.CliCommand
import com.github.flagshipio.jetbrain.dataClass.TargetingKey
import com.github.flagshipio.jetbrain.services.TargetingKeyDataService
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages

class TargetingKeyStore(project: Project) {

    private var targetingKeyDataService: TargetingKeyDataService
    private val cliCommand = CliCommand()

    init {
        targetingKeyDataService = project.getService(TargetingKeyDataService::class.java)
    }

    fun refreshTargetingKey(): List<TargetingKey>? {
        val targetingKeys = cliCommand.listTargetingKeyCli()
        if (targetingKeys != null) {
            targetingKeyDataService.loadState(targetingKeys)
        }
        return targetingKeys
    }

    fun saveTargetingKey(targetingKey: TargetingKey): TargetingKey? {
        val cliResponse = cliCommand.addTargetingKeyCli(targetingKey)
        if (cliResponse != null) {
            targetingKeyDataService.saveTargetingKey(cliResponse)
            Messages.showMessageDialog("Targeting Key saved", "Status", Messages.getInformationIcon())
        }
        return cliResponse
    }

    fun editTargetingKey(targetingKey: TargetingKey, newTargetingKey: TargetingKey): TargetingKey? {
        val cliResponse = targetingKey.id?.let { cliCommand.editTargetingKeyCli(it, newTargetingKey) }
        if (cliResponse != null) {
            targetingKeyDataService.editTargetingKey(targetingKey, cliResponse)
            Messages.showMessageDialog("Targeting Key edited", "Status", Messages.getInformationIcon())
        }
        return cliResponse
    }

    fun deleteTargetingKey(targetingKey: TargetingKey): String? {
        val cliResponse = targetingKey.id?.let { cliCommand.deleteTargetingKeyCli(it) }
        if (cliResponse != null) {
            if (cliResponse.contains("deleted", true)) {
                targetingKeyDataService.deleteTargetingKey(targetingKey)
                Messages.showMessageDialog("Targeting Key deleted", "Status", Messages.getInformationIcon())
            }
        }
        return cliResponse
    }

    fun getTargetingKeys(): List<TargetingKey> {
        return targetingKeyDataService.state
    }
}