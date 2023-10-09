package com.github.flagshipio.jetbrain.action

import com.github.flagshipio.jetbrain.toolWindow.FlagNodeParent
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
        var selectedNode = ActionHelpers.getLastSelectedDefaultMutableListFlagTreeNode(project)
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

    }
}
