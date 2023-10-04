package com.github.flagshipio.jetbrain.store

import com.github.flagshipio.jetbrain.cli.CheckCLI
import com.github.flagshipio.jetbrain.dataClass.Configuration
import com.github.flagshipio.jetbrain.services.ConfigurationDataService
import com.intellij.openapi.project.Project

class ConfigurationStore(project: Project) {

    private var configurationDataService: ConfigurationDataService

    init {
        configurationDataService = project.getService(ConfigurationDataService::class.java)
    }

    fun refreshConfiguration(project: Project): List<Configuration>? {
        val checkCLI = CheckCLI(project)

        return checkCLI.listConfigurationCli(project)

    }

    fun saveConfiguration(project: Project) {
        val configurations = refreshConfiguration(project)
        // Save or retrieve features from the service

        if (configurations != null) {
            configurationDataService.saveConfigurations(configurations)
        }
    }

    fun getConfiguration(project: Project): List<Configuration> {
        return configurationDataService.getConfigurations()
    }
}