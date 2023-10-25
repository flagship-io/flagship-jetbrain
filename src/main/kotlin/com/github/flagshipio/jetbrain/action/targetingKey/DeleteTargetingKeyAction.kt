package com.github.flagshipio.jetbrain.action.targetingKey

import com.github.flagshipio.jetbrain.action.ActionHelpers
import com.github.flagshipio.jetbrain.store.TargetingKeyStore
import com.github.flagshipio.jetbrain.toolWindow.targetingKey.TargetingKeyNodeParent
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
import javax.swing.tree.DefaultMutableTreeNode

class DeleteTargetingKeyAction : AnAction() {
    companion object {
        const val ID = "com.github.flagshipio.jetbrain.action.DeleteTargetingKeyAction"
    }

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return
        val targetingKeyStore = TargetingKeyStore(project)
        var selectedNode = ActionHelpers.getLastSelectedDefaultMutableListTargetingKeyTreeNode(project)
        while (selectedNode != null) {
            if (selectedNode.userObject is TargetingKeyNodeParent) {
                val targetingKeyNodeParent = selectedNode.userObject as TargetingKeyNodeParent
                val resp = Messages.showOkCancelDialog(
                    "Do you want to delete this targeting key ?",
                    "Delete Targeting Key",
                    "Yes",
                    "No",
                    null
                )
                if (resp == 2) {
                    return
                }

                targetingKeyStore.deleteTargetingKey(targetingKeyNodeParent.targetingKey)
                ActionHelpers.getTargetingKeyPanel(project).updateListTargetingKeyBorder()
                ActionHelpers.getListTargetingKeyPanel(project).updateNodeInfo()
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
