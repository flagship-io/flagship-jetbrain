package com.github.flagshipio.jetbrain.action.goal

import com.github.flagshipio.jetbrain.action.ActionHelpers
import com.github.flagshipio.jetbrain.store.GoalStore
import com.github.flagshipio.jetbrain.toolWindow.goal.GoalNodeParent
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
import javax.swing.tree.DefaultMutableTreeNode

class DeleteGoalAction : AnAction() {
    companion object {
        const val ID = "com.github.flagshipio.jetbrain.action.DeleteGoalAction"
    }

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return
        val goalStore = GoalStore(project)
        var selectedNode = ActionHelpers.getLastSelectedDefaultMutableListGoalTreeNode(project)
        while (selectedNode != null) {
            if (selectedNode.userObject is GoalNodeParent) {
                val goalNodeParent = selectedNode.userObject as GoalNodeParent
                val resp = Messages.showOkCancelDialog(
                    "Do you want to delete this goal ?",
                    "Delete Goal",
                    "Yes",
                    "No",
                    null
                )
                if (resp == 2) {
                    return
                }

                goalStore.deleteGoal(goalNodeParent.goal)
                ActionHelpers.getGoalPanel(project).updateListGoalBorder()
                ActionHelpers.getListGoalPanel(project).updateNodeInfo()
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
        val hasKeyPrefix = selectedNode.toString().startsWith(LABEL_PREFIX)

        e.presentation.isEnabledAndVisible = e.presentation.isEnabled && (hasKeyPrefix || isGoalParentNode)
    }
}
