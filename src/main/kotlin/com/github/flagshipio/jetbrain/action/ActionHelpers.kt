package com.github.flagshipio.jetbrain.action

import com.github.flagshipio.jetbrain.toolWindow.ApplicationToolWindow
import com.github.flagshipio.jetbrain.toolWindow.configuration.ConfigurationListPanel
import com.github.flagshipio.jetbrain.toolWindow.configuration.ConfigurationPanel
import com.github.flagshipio.jetbrain.toolWindow.configuration.ManageConfigurationPanel
import com.github.flagshipio.jetbrain.toolWindow.flag.FlagListPanel
import com.github.flagshipio.jetbrain.toolWindow.flag.FlagPanel
import com.github.flagshipio.jetbrain.toolWindow.flag.ManageFlagPanel
import com.github.flagshipio.jetbrain.toolWindow.goal.GoalListPanel
import com.github.flagshipio.jetbrain.toolWindow.goal.GoalPanel
import com.github.flagshipio.jetbrain.toolWindow.goal.ManageGoalPanel
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import javax.swing.tree.DefaultMutableTreeNode

object ActionHelpers {

    fun getLastSelectedDefaultMutableListConfigurationTreeNode(project: Project): DefaultMutableTreeNode? {
        return project.service<ApplicationToolWindow>().getConfigurationPanel().getListConfigurationPanel()
            .tree.lastSelectedPathComponent as? DefaultMutableTreeNode
    }

    fun getLastSelectedDefaultMutableListFlagTreeNode(project: Project): DefaultMutableTreeNode? {
        return project.service<ApplicationToolWindow>().getFlagPanel()
            .getFlagListPanel().tree.lastSelectedPathComponent as? DefaultMutableTreeNode
    }

    fun getLastSelectedDefaultMutableListGoalTreeNode(project: Project): DefaultMutableTreeNode? {
        return project.service<ApplicationToolWindow>().getGoalPanel()
            .getGoalListPanel().tree.lastSelectedPathComponent as? DefaultMutableTreeNode
    }

    fun getListConfigurationPanel(project: Project): ConfigurationListPanel {
        return project.service<ApplicationToolWindow>().getConfigurationPanel().getListConfigurationPanel()
    }

    fun getManageConfigurationPanel(project: Project): ManageConfigurationPanel {
        return project.service<ApplicationToolWindow>().getConfigurationPanel().getManageConfigurationPanel()
    }

    fun getConfigurationPanel(project: Project): ConfigurationPanel {
        return project.service<ApplicationToolWindow>().getConfigurationPanel()
    }

    fun getListFlagPanel(project: Project): FlagListPanel {
        return project.service<ApplicationToolWindow>().getFlagPanel().getFlagListPanel()
    }

    fun getManageFlagPanel(project: Project): ManageFlagPanel {
        return project.service<ApplicationToolWindow>().getFlagPanel().getManageFlagPanel()
    }

    fun getFlagPanel(project: Project): FlagPanel {
        return project.service<ApplicationToolWindow>().getFlagPanel()
    }

    fun getListGoalPanel(project: Project): GoalListPanel {
        return project.service<ApplicationToolWindow>().getGoalPanel().getGoalListPanel()
    }

    fun getManageGoalPanel(project: Project): ManageGoalPanel {
        return project.service<ApplicationToolWindow>().getGoalPanel().getManageGoalPanel()
    }

    fun getGoalPanel(project: Project): GoalPanel {
        return project.service<ApplicationToolWindow>().getGoalPanel()
    }

}
