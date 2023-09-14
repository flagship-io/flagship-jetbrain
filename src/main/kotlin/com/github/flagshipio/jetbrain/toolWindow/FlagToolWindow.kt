package com.github.flagshipio.jetbrain.toolWindow

import com.intellij.openapi.Disposable
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.content.Content
import com.intellij.ui.content.ContentFactory

@Service(Service.Level.PROJECT)
class FlagToolWindow(project: Project) : DumbAware, Disposable {
    private val basePanel: BasePanel = BasePanel(project)
    private val goalTargetingPanel: GoalTargetingPanel = GoalTargetingPanel(project)
    fun initializeBasePanel(toolWindow: ToolWindow) {
        val contentFactory = ContentFactory.getInstance()

        val content: Content = contentFactory.createContent(null, "Quick & Feature Flag", false)
        content.component = basePanel

        toolWindow.contentManager.addContent(content)

    }

    fun initializeGoalTargetingPanel(toolWindow: ToolWindow) {
        val contentFactory = ContentFactory.getInstance()

        val contentGoalTargeting: Content = contentFactory.createContent(null, "Goal & Targeting", false)
        contentGoalTargeting.component = goalTargetingPanel

        toolWindow.contentManager.addContent(contentGoalTargeting)
    }

    fun getBasePanel(): BasePanel {
        return basePanel
    }

    fun getGoalTargetingPanel(): GoalTargetingPanel {
        return goalTargetingPanel
    }

    companion object {
        fun getInstance(project: Project): FlagToolWindow =
            project.getService(FlagToolWindow::class.java)
    }

    override fun dispose() {}
}
