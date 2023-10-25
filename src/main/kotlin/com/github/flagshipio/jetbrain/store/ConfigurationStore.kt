package com.github.flagshipio.jetbrain.store

import com.github.flagshipio.jetbrain.cli.CliCommand
import com.github.flagshipio.jetbrain.dataClass.Configuration
import com.github.flagshipio.jetbrain.services.ConfigurationDataService
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages

class ConfigurationStore(project: Project) {

    private var configurationDataService: ConfigurationDataService
    private val cliCommand = CliCommand()
    private val flagStore = FlagStore(project)
    private val targetingKeyStore = TargetingKeyStore(project)
    private val goalStore = GoalStore(project)

    init {
        configurationDataService = project.getService(ConfigurationDataService::class.java)
    }

    fun refreshConfiguration(): List<Configuration>? {
        val configurationList = cliCommand.listConfigurationCli()
        if (configurationList != null) {
            configurationDataService.loadState(configurationList)
        }
        return configurationList
    }

    fun saveConfiguration(configuration: Configuration): String? {
        val cliResponse = cliCommand.addConfigurationCli(configuration)
        if (cliResponse != null) {
            if (cliResponse.contains("created successfully", true)) {
                configurationDataService.saveConfiguration(configuration)
                Messages.showMessageDialog("Feature Flag saved", "Status", Messages.getInformationIcon())
            }
        }
        return cliResponse
    }

    fun editConfiguration(configuration: Configuration, newConfiguration: Configuration): String? {
        val cliResponse = configuration.name?.let { cliCommand.editConfigurationCli(it, newConfiguration) }
        if (cliResponse != null) {
            if (cliResponse.contains("edited successfully", true)) {
                configurationDataService.editConfiguration(configuration, newConfiguration)
                Messages.showMessageDialog("Configuration edited", "Status", Messages.getInformationIcon())
            }
        }
        return cliResponse
    }

    fun deleteConfiguration(configuration: Configuration): String? {
        val cliResponse = configuration.name?.let { cliCommand.deleteConfigurationCli(it) }
        if (cliResponse != null) {
            if (cliResponse.contains("deleted successfully", true)) {
                configurationDataService.deleteConfiguration(configuration)
                Messages.showMessageDialog("Configuration deleted", "Status", Messages.getInformationIcon())
            }
        }
        return cliResponse
    }

    fun useConfiguration(configuration: Configuration): Boolean {
        val cliResponse = configuration.name?.let { cliCommand.useConfigurationCli(it) }
        if (cliResponse != null) {
            if (cliResponse.contains("selected successfully", true)) {
                flagStore.refreshFlag()
                targetingKeyStore.refreshTargetingKey()
                goalStore.refreshGoal()
                return true
            }
        }
        return false
    }

    fun saveConfigurationFromFile(filePath: String): String? {
        val cliResponse = cliCommand.addConfigurationFromFileCli(filePath)
        refreshConfiguration()
        return cliResponse
    }

    fun getConfigurations(): List<Configuration> {
        return configurationDataService.state
    }
}