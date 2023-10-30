package com.github.flagshipio.jetbrain.toolWindow

import com.github.flagshipio.jetbrain.toolWindow.configuration.ConfigurationPanel
import com.github.flagshipio.jetbrain.toolWindow.flag.FlagPanel
import com.github.flagshipio.jetbrain.toolWindow.goal.GoalPanel
import com.github.flagshipio.jetbrain.toolWindow.project.ProjectPanel
import com.github.flagshipio.jetbrain.toolWindow.targetingKey.TargetingKeyPanel
import com.intellij.openapi.Disposable
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.content.Content
import com.intellij.ui.content.ContentFactory

@Service(Service.Level.PROJECT)
class ApplicationToolWindow(project: Project) : DumbAware, Disposable {
    private val configurationPanel: ConfigurationPanel = ConfigurationPanel(project)
    private val projectPanel: ProjectPanel = ProjectPanel(project)
    private val flagPanel: FlagPanel = FlagPanel(project)
    private val targetingKeyPanel: TargetingKeyPanel = TargetingKeyPanel(project)
    private val goalPanel: GoalPanel = GoalPanel(project)

    fun initializeFlagPanel(toolWindow: ToolWindow) {
        val contentFactory = ContentFactory.getInstance()

        val content: Content = contentFactory.createContent(null, "Feature Flag", false)
        content.component = flagPanel

        toolWindow.contentManager.addContent(content)
    }

    fun initializeGoalPanel(toolWindow: ToolWindow) {
        val contentFactory = ContentFactory.getInstance()

        val contentGoal: Content = contentFactory.createContent(null, "Goal", false)
        contentGoal.component = goalPanel

        toolWindow.contentManager.addContent(contentGoal)
    }

    fun initializeConfigurationPanel(toolWindow: ToolWindow) {
        val contentFactory = ContentFactory.getInstance()

        val contentConfiguration: Content = contentFactory.createContent(null, "Configuration", false)
        contentConfiguration.component = configurationPanel

        toolWindow.contentManager.addContent(contentConfiguration)
    }

    fun initializeTargetingKeyPanel(toolWindow: ToolWindow) {
        val contentFactory = ContentFactory.getInstance()

        val contentGoal: Content = contentFactory.createContent(null, "Targeting Key", false)
        contentGoal.component = targetingKeyPanel

        toolWindow.contentManager.addContent(contentGoal)
    }

    fun initializeProjectPanel(toolWindow: ToolWindow) {
        val contentFactory = ContentFactory.getInstance()

        val contentGoal: Content = contentFactory.createContent(null, "Project", false)
        contentGoal.component = projectPanel

        toolWindow.contentManager.addContent(contentGoal)
    }

    fun getProjectPanel(): ProjectPanel {
        return projectPanel
    }

    fun getFlagPanel(): FlagPanel {
        return flagPanel
    }

    fun getConfigurationPanel(): ConfigurationPanel {
        return configurationPanel
    }

    fun getGoalPanel(): GoalPanel {
        return goalPanel
    }

    fun getTargetingKeyPanel(): TargetingKeyPanel {
        return targetingKeyPanel
    }

    companion object {
        fun getInstance(project: Project): ApplicationToolWindow =
            project.getService(ApplicationToolWindow::class.java)
    }

    override fun dispose() {}
}
