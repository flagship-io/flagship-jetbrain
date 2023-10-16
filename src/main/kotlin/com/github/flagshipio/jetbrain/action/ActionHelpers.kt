package com.github.flagshipio.jetbrain.action

import com.github.flagshipio.jetbrain.toolWindow.ApplicationToolWindow
import com.github.flagshipio.jetbrain.toolWindow.configuration.ConfigurationListPanel
import com.github.flagshipio.jetbrain.toolWindow.configuration.ManageConfigurationPanel
import com.github.flagshipio.jetbrain.toolWindow.linkflag.FlagListPanel
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import javax.swing.tree.DefaultMutableTreeNode

object ActionHelpers {
    fun getLastSelectedDefaultMutableListFlagTreeNode(project: Project): DefaultMutableTreeNode? {
        return project.service<ApplicationToolWindow>().getFlagPanel()
            .getFlagListPanel().tree.lastSelectedPathComponent as? DefaultMutableTreeNode
    }

    fun getLastSelectedDefaultMutableListConfigurationTreeNode(project: Project): DefaultMutableTreeNode? {
        return project.service<ApplicationToolWindow>().getConfigurationPanel().getListConfigurationPanel()
            .tree.lastSelectedPathComponent as? DefaultMutableTreeNode
    }

    fun getListConfigurationPanel(project: Project): ConfigurationListPanel {
        return project.service<ApplicationToolWindow>().getConfigurationPanel().getListConfigurationPanel()
    }

    fun getManageConfigurationPanel(project: Project): ManageConfigurationPanel {
        return project.service<ApplicationToolWindow>().getConfigurationPanel().getManageConfigurationPanel()
    }

    fun getListFlagPanel(project: Project): FlagListPanel {
        return project.service<ApplicationToolWindow>().getFlagPanel().getFlagListPanel()
    }
}
