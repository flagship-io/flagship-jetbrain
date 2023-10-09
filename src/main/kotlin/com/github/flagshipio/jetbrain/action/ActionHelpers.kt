package com.github.flagshipio.jetbrain.action

import com.github.flagshipio.jetbrain.toolWindow.FlagToolWindow
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import javax.swing.tree.DefaultMutableTreeNode

object ActionHelpers {
    fun getLastSelectedDefaultMutableListFlagTreeNode(project: Project): DefaultMutableTreeNode? {
        return project.service<FlagToolWindow>().getBasePanel()
            .getFlagPanel().tree.lastSelectedPathComponent as? DefaultMutableTreeNode
    }

    fun getLastSelectedDefaultMutableListConfigurationTreeNode(project: Project): DefaultMutableTreeNode? {
        return project.service<FlagToolWindow>().getConfigurationPanel().getListConfigurationPanel()
            .tree.lastSelectedPathComponent as? DefaultMutableTreeNode
    }
}
