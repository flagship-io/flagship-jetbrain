package com.github.flagshipio.jetbrain.action.flag

import com.github.flagshipio.jetbrain.action.ActionHelpers
import com.github.flagshipio.jetbrain.toolWindow.flag.FlagNodeParent
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import javax.swing.tree.DefaultMutableTreeNode


class EditFlagAction : AnAction() {
    companion object {
        const val ID = "com.github.flagshipio.jetbrain.action.EditFlagAction"
    }

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return
        var selectedNode = ActionHelpers.getLastSelectedDefaultMutableListFlagTreeNode(project)
        while (selectedNode != null) {
            if (selectedNode.userObject is FlagNodeParent) {
                val flagNodeParent = selectedNode.userObject as FlagNodeParent
                val editFlagPanel = ActionHelpers.getManageFlagPanel(project)
                editFlagPanel.updateContent(editFlagPanel.featureFlagFrame(flagNodeParent.flag))
                return
            }
            selectedNode = selectedNode.parent as? DefaultMutableTreeNode
        }
    }

    override fun update(e: AnActionEvent) {
        super.update(e)
        val project = e.project
        val selectedNode = ActionHelpers.getLastSelectedDefaultMutableListFlagTreeNode(project!!)
        val isFlagParentNode = selectedNode!!.userObject is FlagNodeParent
        val hasNamePrefix = selectedNode.toString().startsWith(KEY_PREFIX)

        e.presentation.isEnabledAndVisible = e.presentation.isEnabled && (hasNamePrefix || isFlagParentNode)
    }
}
