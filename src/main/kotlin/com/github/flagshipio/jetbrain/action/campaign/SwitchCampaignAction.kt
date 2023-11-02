package com.github.flagshipio.jetbrain.action.project

import com.github.flagshipio.jetbrain.action.ActionHelpers
import com.github.flagshipio.jetbrain.store.ProjectStore
import com.github.flagshipio.jetbrain.toolWindow.project.ProjectNodeParent
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
import javax.swing.tree.DefaultMutableTreeNode

class SwitchCampaignAction : AnAction() {
    companion object {
        const val ID = "com.github.flagshipio.jetbrain.action.SwitchProjectAction"
    }

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return
        val projectStates = arrayOf("active", "paused", "interrupted")
        val projectStore = ProjectStore(project)
        var selectedNode = ActionHelpers.getLastSelectedDefaultMutableListProjectTreeNode(project)
        while (selectedNode != null) {
            if (selectedNode.userObject is ProjectNodeParent) {
                val projectNodeParent = selectedNode.userObject as ProjectNodeParent
                val selectedProjectState = Messages.showEditableChooseDialog(
                    "Select a state for the project:",
                    "Project State",
                    Messages.getInformationIcon(),
                    projectStates,
                    projectStates[0],
                    null
                )

                projectStore.switchProject(projectNodeParent.project, selectedProjectState!!)
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
