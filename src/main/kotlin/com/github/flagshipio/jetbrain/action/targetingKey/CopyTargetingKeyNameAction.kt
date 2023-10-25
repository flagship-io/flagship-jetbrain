package com.github.flagshipio.jetbrain.action.targetingKey

import com.github.flagshipio.jetbrain.action.ActionHelpers
import com.github.flagshipio.jetbrain.toolWindow.targetingKey.TargetingKeyNodeParent
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.StringSelection
import javax.swing.tree.DefaultMutableTreeNode


const val NAME_PREFIX = "Name:"

class CopyTargetingKeyNameAction : AnAction() {
    companion object {
        const val ID = "com.github.flagshipio.jetbrain.action.CopyTargetingKeyNameAction"
    }

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return
        var selectedNode = ActionHelpers.getLastSelectedDefaultMutableListTargetingKeyTreeNode(project)
        while (selectedNode != null) {
            if (selectedNode.userObject is TargetingKeyNodeParent) {
                val targetingKeyNodeParent = selectedNode.userObject as TargetingKeyNodeParent
                val selection = StringSelection(targetingKeyNodeParent.name_)
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
        val selectedNode = ActionHelpers.getLastSelectedDefaultMutableListTargetingKeyTreeNode(project!!)
        val isTargetingKeyParentNode = selectedNode!!.userObject is TargetingKeyNodeParent
        val hasNamePrefix = selectedNode.toString().startsWith(NAME_PREFIX)

        e.presentation.isEnabledAndVisible = e.presentation.isEnabled && (hasNamePrefix || isTargetingKeyParentNode)
    }

}