package com.github.flagshipio.jetbrain.action.flag

import com.github.flagshipio.jetbrain.action.ActionHelpers
import com.github.flagshipio.jetbrain.store.FlagStore
import com.github.flagshipio.jetbrain.toolWindow.flag.FlagNodeParent
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
import javax.swing.tree.DefaultMutableTreeNode

class DeleteFlagAction : AnAction() {
    companion object {
        const val ID = "com.github.flagshipio.jetbrain.action.DeleteFlagAction"
    }

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return
        val flagStore = FlagStore(project)
        var selectedNode = ActionHelpers.getLastSelectedDefaultMutableListFlagTreeNode(project)
        while (selectedNode != null) {
            if (selectedNode.userObject is FlagNodeParent) {
                val flagNodeParent = selectedNode.userObject as FlagNodeParent
                val resp = Messages.showOkCancelDialog(
                    "Do you want to delete this flag ?",
                    "Delete Flag",
                    "Yes",
                    "No",
                    null
                )
                if (resp == 2) {
                    return
                }
                flagStore.deleteFlag(flagNodeParent.flag)
                ActionHelpers.getListFlagPanel(project).updateNodeInfo()
                Messages.showMessageDialog("Flag deleted", "Status", Messages.getInformationIcon())
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
        val hasKeyPrefix = selectedNode.toString().startsWith(KEY_PREFIX)

        e.presentation.isEnabledAndVisible = e.presentation.isEnabled && (hasKeyPrefix || isFlagParentNode)
    }
}
