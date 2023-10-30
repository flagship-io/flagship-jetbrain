package com.github.flagshipio.jetbrain.toolWindow

import com.github.flagshipio.jetbrain.cli.Cli
import com.github.flagshipio.jetbrain.store.*
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
        val flagStore = FlagStore(project)
        val configurationStore = ConfigurationStore(project)
        val goalStore = GoalStore(project)
        val targetingKeyStore = TargetingKeyStore(project)
        val projectStore = ProjectStore(project)

        configurationStore.refreshConfiguration()
        projectStore.refreshProject()
        flagStore.refreshFlag()
        goalStore.refreshGoal()
        targetingKeyStore.refreshTargetingKey()

        val applicationToolWindow = ApplicationToolWindow.getInstance(project)

        applicationToolWindow.initializeConfigurationPanel(toolWindow)
        applicationToolWindow.initializeProjectPanel(toolWindow)
        applicationToolWindow.initializeFlagPanel(toolWindow)
        applicationToolWindow.initializeTargetingKeyPanel(toolWindow)
        applicationToolWindow.initializeGoalPanel(toolWindow)
    }

    override fun shouldBeAvailable(project: Project) = true

}
