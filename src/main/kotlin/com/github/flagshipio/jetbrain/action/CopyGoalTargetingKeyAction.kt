package com.github.flagshipio.jetbrain.action

import com.github.flagshipio.jetbrain.toolWindow.FlagNodeParent
import com.github.flagshipio.jetbrain.toolWindow.KEY_PREFIX
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.StringSelection
import javax.swing.tree.DefaultMutableTreeNode
class CopyGoalTargetingKeyAction : AnAction() {
    companion object {
        const val ID = "com.github.flagshipio.jetbrain.action.CopyKeyAction"
    }

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return
        var selectedNode = ActionHelpers.getLastSelectedDefaultMutableTreeNode1(project)
        while (selectedNode != null) {
            if (selectedNode.userObject is FlagNodeParent) {
                val flagNodeParent = selectedNode.userObject as FlagNodeParent
                val selection = StringSelection(flagNodeParent.key)
                val clipboard: Clipboard = Toolkit.getDefaultToolkit().systemClipboard
                return clipboard.setContents(selection, selection)
            } else {
                selectedNode = selectedNode.parent as? DefaultMutableTreeNode
            }
        }
    }

    override fun update(e: AnActionEvent) {
        super.update(e)
        val project = e.project
        val selectedNode = ActionHelpers.getLastSelectedDefaultMutableTreeNode1(project!!)
        val isFlagParentNode = selectedNode!!.userObject is FlagNodeParent
        val hasKeyPrefix = selectedNode.toString().startsWith(KEY_PREFIX)

        e.presentation.isEnabledAndVisible = e.presentation.isEnabled && (hasKeyPrefix || isFlagParentNode)
    }
}
