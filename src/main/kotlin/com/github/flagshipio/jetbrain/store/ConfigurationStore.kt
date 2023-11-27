package com.github.flagshipio.jetbrain.store

import com.github.flagshipio.jetbrain.cli.CliCommand
import com.github.flagshipio.jetbrain.dataClass.Configuration
import com.github.flagshipio.jetbrain.services.ConfigurationDataService
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages

class ConfigurationStore(var project: Project) {

    private var configurationDataService: ConfigurationDataService =
        project.getService(ConfigurationDataService::class.java)
    private val cliCommand = CliCommand()
    private val flagStore = FlagStore(project)
    private val projectStore = ProjectStore(project)
    private val targetingKeyStore = TargetingKeyStore(project)
    private val goalStore = GoalStore(project)

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
                Messages.showMessageDialog("Configuration saved", "Status", Messages.getInformationIcon())
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
                ProgressManager.getInstance().runProcessWithProgressSynchronously(
                    Runnable {
                        val progressIndicator: ProgressIndicator? = ProgressManager.getInstance().progressIndicator
                        progressIndicator?.fraction = 0.1
                        progressIndicator?.text = "Loading projects and campaigns..."
                        projectStore.refreshProject()
                        progressIndicator?.fraction = 0.5
                        progressIndicator?.text = "Loading flags..."
                        flagStore.refreshFlag()
                        progressIndicator?.fraction = 0.7
                        progressIndicator?.text = "Loading targeting keys..."
                        targetingKeyStore.refreshTargetingKey()
                        progressIndicator?.fraction = 0.9
                        progressIndicator?.text = "Loading goals..."
                        goalStore.refreshGoal()
                        progressIndicator?.fraction = 1.0
                    },
                    "Loading Flagship Resources...",
                    false,
                    project
                )

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

    fun getCurrentConfiguration(): Configuration? {
        return cliCommand.currentConfigurationCli()
    }
}