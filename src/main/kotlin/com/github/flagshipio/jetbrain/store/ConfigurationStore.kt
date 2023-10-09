package com.github.flagshipio.jetbrain.store

import com.github.flagshipio.jetbrain.cli.CheckCLI
import com.github.flagshipio.jetbrain.dataClass.Configuration
import com.github.flagshipio.jetbrain.services.ConfigurationDataService
import com.intellij.openapi.project.Project

class ConfigurationStore(project: Project) {

    private var configurationDataService: ConfigurationDataService
    private val checkCLI = CheckCLI()

    init {
        configurationDataService = project.getService(ConfigurationDataService::class.java)
    }

    fun refreshConfiguration(): List<Configuration>? {
        val configurationList = checkCLI.listConfigurationCli()
        if (configurationList != null) {
            configurationDataService.loadState(configurationList)
        }
        return configurationList
    }

    fun saveConfiguration(configuration: Configuration): String? {
        val cliResponse = checkCLI.addConfigurationCli(configuration)
        if (cliResponse != null) {
            if (cliResponse.contains("created successfully", true)) {
                configurationDataService.saveConfiguration(configuration)
            }
        }
        return cliResponse
    }

    fun deleteConfiguration(configuration: Configuration): String? {
        val cliResponse = configuration.name?.let { checkCLI.deleteConfigurationCli(it) }
        if (cliResponse != null) {
            if (cliResponse.contains("deleted successfully", true)) {
                configurationDataService.deleteConfiguration(configuration)
            }
        }
        return cliResponse
    }

    fun saveConfigurationFromFile(filePath: String): String? {
        val cliResponse = checkCLI.addConfigurationFromFileCli(filePath)
        refreshConfiguration()
        return cliResponse
    }

    fun getConfigurations(): List<Configuration> {
        return configurationDataService.state
    }
}