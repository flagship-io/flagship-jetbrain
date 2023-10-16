package com.github.flagshipio.jetbrain.store

import com.github.flagshipio.jetbrain.cli.CliCommand
import com.github.flagshipio.jetbrain.dataClass.Configuration
import com.github.flagshipio.jetbrain.services.ConfigurationDataService
import com.intellij.openapi.project.Project

class ConfigurationStore(project: Project) {

    private var configurationDataService: ConfigurationDataService
    private val cliCommand = CliCommand()
    private val flagStore = FlagStore(project)

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
            }
        }
        return cliResponse
    }

    fun editConfiguration(configuration: Configuration, newConfiguration: Configuration): String? {
        val cliResponse = configuration.name?.let { cliCommand.editConfigurationCli(it, newConfiguration) }
        if (cliResponse != null) {
            if (cliResponse.contains("edited successfully", true)) {
                configurationDataService.editConfiguration(configuration, newConfiguration)
            }
        }
        return cliResponse
    }

    fun deleteConfiguration(configuration: Configuration): String? {
        val cliResponse = configuration.name?.let { cliCommand.deleteConfigurationCli(it) }
        if (cliResponse != null) {
            if (cliResponse.contains("deleted successfully", true)) {
                configurationDataService.deleteConfiguration(configuration)
            }
        }
        return cliResponse
    }

    fun useConfiguration(configuration: Configuration): Boolean {
        val cliResponse = configuration.name?.let { cliCommand.useConfigurationCli(it) }
        if (cliResponse != null) {
            if (cliResponse.contains("selected successfully", true)) {
                flagStore.refreshFlag()
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