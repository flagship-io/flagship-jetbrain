package com.github.flagshipio.jetbrain.toolWindow.project

import com.github.flagshipio.jetbrain.store.ProjectStore
import com.intellij.openapi.project.Project
import com.intellij.ui.OnePixelSplitter
import java.awt.BorderLayout
import javax.swing.BorderFactory
import javax.swing.JPanel
import javax.swing.border.Border

class ProjectPanel(project: Project) : JPanel() {
    private val splitter = OnePixelSplitter(true, "ProjectSplitter", .05f)
    private val projectStore = ProjectStore(project)
    private val listProjectTitle = "List Project"

    private val listProjectPanel = ProjectListPanel(project)
    private val manageProjectPanel = ManageProjectPanel(project, projectStore)

    init {

        val manageProjectBorder: Border = BorderFactory.createTitledBorder("Manage Project")
        val listProjectBorder: Border = BorderFactory.createTitledBorder(
            listProjectTitle + " (" + projectStore.getProjects().count() + " Projects)"
        )

        manageProjectPanel.border = manageProjectBorder
        listProjectPanel.border = listProjectBorder

        layout = BorderLayout(0, 0)
        splitter.apply {
            setResizeEnabled(true)
            firstComponent = manageProjectPanel
            secondComponent = listProjectPanel
        }
        add(splitter, BorderLayout.CENTER)
    }

    fun updateListProjectBorder() {
        this.listProjectPanel.border = BorderFactory.createTitledBorder(
            listProjectTitle + " (" + projectStore.getProjects().count() + " Projects)"
        )
    }

    fun getProjectListPanel(): ProjectListPanel {
        return listProjectPanel
    }

    fun getManageProjectPanel(): ManageProjectPanel {
        return manageProjectPanel
    }
}
