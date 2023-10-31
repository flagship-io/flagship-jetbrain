package com.github.flagshipio.jetbrain.action.goal

import com.github.flagshipio.jetbrain.action.ActionHelpers
import com.github.flagshipio.jetbrain.toolWindow.goal.GoalNodeParent
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.StringSelection
import javax.swing.tree.DefaultMutableTreeNode

class CopyGoalLabelAction : AnAction() {
    companion object {
        const val ID = "com.github.flagshipio.jetbrain.action.CopyGoalLabelAction"
    }

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return
        var selectedNode = ActionHelpers.getLastSelectedDefaultMutableListGoalTreeNode(project)
        while (selectedNode != null) {
            if (selectedNode.userObject is GoalNodeParent) {
                val goalNodeParent = selectedNode.userObject as GoalNodeParent
                val selection = StringSelection(goalNodeParent.label)
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
        val selectedNode = ActionHelpers.getLastSelectedDefaultMutableListGoalTreeNode(project!!)
        val isGoalParentNode = selectedNode!!.userObject is GoalNodeParent
        val hasLabelPrefix = selectedNode.toString().startsWith(LABEL_PREFIX)

        e.presentation.isEnabledAndVisible = e.presentation.isEnabled && (hasLabelPrefix || isGoalParentNode)
    }

}