package com.github.flagshipio.jetbrain.action.flag

import com.github.flagshipio.jetbrain.action.ActionHelpers
import com.github.flagshipio.jetbrain.toolWindow.flag.FlagNodeParent
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.StringSelection
import javax.swing.tree.DefaultMutableTreeNode

const val KEY_PREFIX = "Key:"

class CopyFlagIdAction : AnAction() {
    companion object {
        const val ID = "com.github.flagshipio.jetbrain.action.CopyFlagIdAction"
    }

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return
        var selectedNode = ActionHelpers.getLastSelectedDefaultMutableListFlagTreeNode(project)
        while (selectedNode != null) {
            if (selectedNode.userObject is FlagNodeParent) {
                val flagNodeParent = selectedNode.userObject as FlagNodeParent
                val selection = StringSelection(flagNodeParent.flag.id)
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
        val selectedNode = ActionHelpers.getLastSelectedDefaultMutableListFlagTreeNode(project!!)
        val isFlagParentNode = selectedNode!!.userObject is FlagNodeParent
        val hasKeyPrefix = selectedNode.toString().startsWith(KEY_PREFIX)

        e.presentation.isEnabledAndVisible = e.presentation.isEnabled && (hasKeyPrefix || isFlagParentNode)
    }

}