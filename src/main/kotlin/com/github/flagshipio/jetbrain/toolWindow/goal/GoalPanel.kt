package com.github.flagshipio.jetbrain.toolWindow.goal

import com.github.flagshipio.jetbrain.store.GoalStore
import com.intellij.openapi.project.Project
import com.intellij.ui.OnePixelSplitter
import java.awt.BorderLayout
import javax.swing.BorderFactory
import javax.swing.JPanel
import javax.swing.border.Border

class GoalPanel(project: Project) : JPanel() {
    private val splitter = OnePixelSplitter(true, "GoalSplitter", .05f)
    private val goalStore = GoalStore(project)
    private val listGoalTitle = "List Goal"

    private val listGoalPanel = GoalListPanel(project)
    private val manageGoalPanel = ManageGoalPanel(project, goalStore)

    init {

        val manageGoalBorder: Border = BorderFactory.createTitledBorder("Manage Goal")
        val listGoalBorder: Border =
            BorderFactory.createTitledBorder(listGoalTitle + " (" + goalStore.getGoals().count() + " Goals)")

        manageGoalPanel.border = manageGoalBorder
        listGoalPanel.border = listGoalBorder

        layout = BorderLayout(0, 0)
        splitter.apply {
            setResizeEnabled(true)
            firstComponent = manageGoalPanel
            secondComponent = listGoalPanel
        }
        add(splitter, BorderLayout.CENTER)
    }

    fun updateListGoalBorder() {
        this.listGoalPanel.border =
            BorderFactory.createTitledBorder(listGoalTitle + " (" + goalStore.getGoals().count() + " Goals)")
    }

    fun getGoalListPanel(): GoalListPanel {
        return listGoalPanel
    }

    fun getManageGoalPanel(): ManageGoalPanel {
        return manageGoalPanel
    }
}
