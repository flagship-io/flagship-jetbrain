package com.github.flagshipio.jetbrain.action.project

import com.github.flagshipio.jetbrain.action.ActionHelpers
import com.github.flagshipio.jetbrain.store.ProjectStore
import com.github.flagshipio.jetbrain.toolWindow.project.ProjectNodeParent
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
import javax.swing.tree.DefaultMutableTreeNode

class DeleteProjectAction : AnAction() {
    companion object {
        const val ID = "com.github.flagshipio.jetbrain.action.DeleteProjectAction"
    }

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return
        val projectStore = ProjectStore(project)
        var selectedNode = ActionHelpers.getLastSelectedDefaultMutableListProjectTreeNode(project)
        while (selectedNode != null) {
            if (selectedNode.userObject is ProjectNodeParent) {
                val projectNodeParent = selectedNode.userObject as ProjectNodeParent
                val resp = Messages.showOkCancelDialog(
                    "Do you want to delete this project ?",
                    "Delete Project",
                    "Yes",
                    "No",
                    null
                )
                if (resp == 2) {
                    return
                }

                projectStore.deleteProject(projectNodeParent.project)
                ActionHelpers.getProjectPanel(project).updateListProjectBorder()
                ActionHelpers.getListProjectPanel(project).updateNodeInfo()
                return
            }
            selectedNode = selectedNode.parent as? DefaultMutableTreeNode

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
