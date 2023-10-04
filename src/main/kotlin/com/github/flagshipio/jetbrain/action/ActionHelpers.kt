package com.github.flagshipio.jetbrain.action

import com.github.flagshipio.jetbrain.toolWindow.FlagToolWindow
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import javax.swing.tree.DefaultMutableTreeNode

object ActionHelpers {
    fun getLastSelectedDefaultMutableTreeNode(project: Project): DefaultMutableTreeNode? {
        return project.service<FlagToolWindow>().getBasePanel()
            .getFlagPanel().tree.lastSelectedPathComponent as? DefaultMutableTreeNode
    }

    fun getLastSelectedDefaultMutableTreeNode1(project: Project): DefaultMutableTreeNode? {
        return project.service<FlagToolWindow>().getGoalTargetingPanel()
            .getFlagPanel().tree.lastSelectedPathComponent as? DefaultMutableTreeNode
    }
}
