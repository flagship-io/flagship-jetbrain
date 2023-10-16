package com.github.flagshipio.jetbrain.toolWindow

import com.github.flagshipio.jetbrain.toolWindow.configuration.ConfigurationPanel
import com.github.flagshipio.jetbrain.toolWindow.goaltargeting.GoalTargetingPanel
import com.github.flagshipio.jetbrain.toolWindow.flag.FlagPanel
import com.intellij.openapi.Disposable
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.content.Content
import com.intellij.ui.content.ContentFactory

@Service(Service.Level.PROJECT)
class ApplicationToolWindow(project: Project) : DumbAware, Disposable {
    private val flagPanel: FlagPanel = FlagPanel(project)
    private val goalTargetingPanel: GoalTargetingPanel = GoalTargetingPanel(project)
    private val configurationPanel: ConfigurationPanel = ConfigurationPanel(project)
    fun initializeBasePanel(toolWindow: ToolWindow) {
        val contentFactory = ContentFactory.getInstance()

        val content: Content = contentFactory.createContent(null, "Feature Flag", false)
        content.component = flagPanel

        toolWindow.contentManager.addContent(content)

    }

    fun initializeGoalTargetingPanel(toolWindow: ToolWindow) {
        val contentFactory = ContentFactory.getInstance()

        val contentGoalTargeting: Content = contentFactory.createContent(null, "Goal & Targeting", false)
        contentGoalTargeting.component = goalTargetingPanel

        toolWindow.contentManager.addContent(contentGoalTargeting)
    }

    fun initializeConfigurationPanel(toolWindow: ToolWindow) {
        val contentFactory = ContentFactory.getInstance()

        val contentConfiguration: Content = contentFactory.createContent(null, "Configuration", false)
        contentConfiguration.component = configurationPanel

        toolWindow.contentManager.addContent(contentConfiguration)
    }

    fun getFlagPanel(): FlagPanel {
        return flagPanel
    }

    fun getConfigurationPanel(): ConfigurationPanel {
        return configurationPanel
    }

    fun getGoalTargetingPanel(): GoalTargetingPanel {
        return goalTargetingPanel
    }

    companion object {
        fun getInstance(project: Project): ApplicationToolWindow =
            project.getService(ApplicationToolWindow::class.java)
    }

    override fun dispose() {}
}
