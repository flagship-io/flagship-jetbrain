package com.github.flagshipio.jetbrain.toolWindow

import com.github.flagshipio.jetbrain.cli.Cli
import com.github.flagshipio.jetbrain.store.ConfigurationStore
import com.github.flagshipio.jetbrain.store.FeatureStore
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory


class MyToolWindowFactory : ToolWindowFactory {

    init {
        val cli = Cli()
        cli.downloadCli()

        thisLogger().warn("Don't forget to remove all non-needed sample code files with their corresponding registration entries in `plugin.xml`.")
    }

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val flagStore = FeatureStore(project)
        val configurationStore = ConfigurationStore(project)

        flagStore.refreshFeatureFlag()
        configurationStore.refreshConfiguration()

        val flagToolWindow = FlagToolWindow.getInstance(project)
        flagToolWindow.initializeConfigurationPanel(toolWindow)
        flagToolWindow.initializeBasePanel(toolWindow)
        flagToolWindow.initializeGoalTargetingPanel(toolWindow)
    }

    override fun shouldBeAvailable(project: Project) = true

}
