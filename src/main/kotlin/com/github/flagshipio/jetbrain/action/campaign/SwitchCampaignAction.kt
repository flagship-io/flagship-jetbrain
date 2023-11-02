package com.github.flagshipio.jetbrain.action.campaign

import com.github.flagshipio.jetbrain.action.ActionHelpers
import com.github.flagshipio.jetbrain.cli.CliCommand
import com.github.flagshipio.jetbrain.store.ProjectStore
import com.github.flagshipio.jetbrain.toolWindow.project.ProjectNodeParent
import com.github.flagshipio.jetbrain.toolWindow.project.campaign.CampaignListNodeParent
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.ui.Messages
import javax.swing.tree.DefaultMutableTreeNode

class SwitchCampaignAction : AnAction() {

    private val cliCommand = CliCommand()
    companion object {
        const val ID = "com.github.flagshipio.jetbrain.action.SwitchCampaignAction"
    }

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return
        val projectStore = ProjectStore(project)
        val campaignStates = arrayOf("active", "paused", "interrupted")
        var selectedNode = ActionHelpers.getLastSelectedDefaultMutableListProjectTreeNode(project)
        while (selectedNode != null) {
            if (selectedNode.userObject is CampaignListNodeParent) {
                val campaignNodeParent = selectedNode.userObject as CampaignListNodeParent
                val selectedProjectState = Messages.showEditableChooseDialog(
                    "Select a state for the campaign:",
                    "Campaign State",
                    Messages.getInformationIcon(),
                    campaignStates,
                    campaignStates[0],
                    null
                )
                campaignNodeParent.campaign.id?.let { cliCommand.switchCampaignStateCli(it, selectedProjectState!!) }
                ProgressManager.getInstance().runProcessWithProgressSynchronously(
                    Runnable {
                        val progressIndicator: ProgressIndicator? = ProgressManager.getInstance().progressIndicator
                        progressIndicator?.fraction = 0.1
                        progressIndicator?.text = "Loading projects and campaigns..."
                        progressIndicator?.fraction = 0.5
                        projectStore.refreshProject()
                        progressIndicator?.fraction = 1.0
                    },
                    "Loading Flagship Resources...",
                    false,
                    project
                )

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
        val isCampaignParentNode = selectedNode!!.userObject is CampaignListNodeParent
        val hasNamePrefix = selectedNode.toString().startsWith(NAME_PREFIX)

        e.presentation.isEnabledAndVisible = e.presentation.isEnabled && (hasNamePrefix || isCampaignParentNode)
    }
}
