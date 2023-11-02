package com.github.flagshipio.jetbrain.action.campaign

import com.github.flagshipio.jetbrain.action.ActionHelpers
import com.github.flagshipio.jetbrain.toolWindow.project.campaign.CampaignListNodeParent
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.StringSelection
import javax.swing.tree.DefaultMutableTreeNode

val NAME_PREFIX = "Name:"

class CopyCampaignIdAction : AnAction() {
    companion object {
        const val ID = "com.github.flagshipio.jetbrain.action.CopyCampaignIdAction"
    }

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return
        var selectedNode = ActionHelpers.getLastSelectedDefaultMutableListProjectTreeNode(project)
        while (selectedNode != null) {
            if (selectedNode.userObject is CampaignListNodeParent) {
                val campaignNodeParent = selectedNode.userObject as CampaignListNodeParent
                val campaignSelection = campaignNodeParent.campaign
                val selection = StringSelection(campaignSelection.id)
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
        val isCampaignParentNode = selectedNode!!.userObject is CampaignListNodeParent
        val hasNamePrefix = selectedNode.toString().startsWith(NAME_PREFIX)

        e.presentation.isEnabledAndVisible = e.presentation.isEnabled && (hasNamePrefix || isCampaignParentNode)
    }

}