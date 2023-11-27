package com.github.flagshipio.jetbrain.action.project

import com.github.flagshipio.jetbrain.action.ActionHelpers
import com.github.flagshipio.jetbrain.toolWindow.project.ProjectNodeParent
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.StringSelection
import javax.swing.tree.DefaultMutableTreeNode


class CopyProjectNameAction : AnAction() {
    companion object {
        const val ID = "com.github.flagshipio.jetbrain.action.CopyProjectNameAction"
    }

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return
        var selectedNode = ActionHelpers.getLastSelectedDefaultMutableListProjectTreeNode(project)
        while (selectedNode != null) {
            if (selectedNode.userObject is ProjectNodeParent) {
                val projectNodeParent = selectedNode.userObject as ProjectNodeParent
                val selection = StringSelection(projectNodeParent.name_)
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
        val selectedNode = ActionHelpers.getLastSelectedDefaultMutableListProjectTreeNode(project!!)
        val isProjectParentNode = selectedNode!!.userObject is ProjectNodeParent
        val hasNamePrefix = selectedNode.toString().startsWith(NAME_PREFIX)

        e.presentation.isEnabledAndVisible = e.presentation.isEnabled && (hasNamePrefix || isProjectParentNode)
    }

}