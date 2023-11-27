package com.github.flagshipio.jetbrain.action.targetingKey

import com.github.flagshipio.jetbrain.action.ActionHelpers
import com.github.flagshipio.jetbrain.toolWindow.targetingKey.TargetingKeyNodeParent
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import javax.swing.tree.DefaultMutableTreeNode


class EditTargetingKeyAction : AnAction() {
    companion object {
        const val ID = "com.github.flagshipio.jetbrain.action.EditTargetingKeyAction"
    }

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return
        var selectedNode = ActionHelpers.getLastSelectedDefaultMutableListTargetingKeyTreeNode(project)
        while (selectedNode != null) {
            if (selectedNode.userObject is TargetingKeyNodeParent) {
                val targetingKeyNodeParent = selectedNode.userObject as TargetingKeyNodeParent
                val editTargetingKeyPanel = ActionHelpers.getManageTargetingKeyPanel(project)
                editTargetingKeyPanel.updateContent(editTargetingKeyPanel.targetingKeyFrame(targetingKeyNodeParent.targetingKey))
                return
            }
            selectedNode = selectedNode.parent as? DefaultMutableTreeNode
        }
    }

    override fun update(e: AnActionEvent) {
        super.update(e)
        val project = e.project
        val selectedNode = ActionHelpers.getLastSelectedDefaultMutableListTargetingKeyTreeNode(project!!)
        val isTargetingKeyParentNode = selectedNode!!.userObject is TargetingKeyNodeParent
        val hasNamePrefix = selectedNode.toString().startsWith(NAME_PREFIX)

        e.presentation.isEnabledAndVisible = e.presentation.isEnabled && (hasNamePrefix || isTargetingKeyParentNode)
    }
}
