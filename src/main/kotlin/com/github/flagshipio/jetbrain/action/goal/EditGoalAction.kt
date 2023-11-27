package com.github.flagshipio.jetbrain.action.goal

import com.github.flagshipio.jetbrain.action.ActionHelpers
import com.github.flagshipio.jetbrain.toolWindow.goal.GoalNodeParent
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import javax.swing.tree.DefaultMutableTreeNode


class EditGoalAction : AnAction() {
    companion object {
        const val ID = "com.github.flagshipio.jetbrain.action.EditGoalAction"
    }

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return
        var selectedNode = ActionHelpers.getLastSelectedDefaultMutableListGoalTreeNode(project)
        while (selectedNode != null) {
            if (selectedNode.userObject is GoalNodeParent) {
                val goalNodeParent = selectedNode.userObject as GoalNodeParent
                val editGoalPanel = ActionHelpers.getManageGoalPanel(project)
                editGoalPanel.updateContent(editGoalPanel.goalFrame(goalNodeParent.goal))
                return
            }
            selectedNode = selectedNode.parent as? DefaultMutableTreeNode
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
