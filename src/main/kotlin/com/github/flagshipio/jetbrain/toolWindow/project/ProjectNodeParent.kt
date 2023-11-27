package com.github.flagshipio.jetbrain.toolWindow.project

import com.github.flagshipio.jetbrain.dataClass.Campaign
import com.github.flagshipio.jetbrain.toolWindow.project.campaign.*
import com.intellij.icons.AllIcons
import com.intellij.ide.projectView.PresentationData
import com.intellij.ui.treeStructure.SimpleNode

const val NAME_PREFIX = "Name:"

class ProjectNodeParent(private var viewModel: ProjectNodeViewModel) : SimpleNode() {
    private var children: MutableList<SimpleNode> = ArrayList()
    val name_: String? get() = viewModel.project.name

    val project get() = viewModel.project

    override fun getChildren(): Array<SimpleNode> {
        if (children.isEmpty()) {
            buildChildren()
        } else {
            children = ArrayList()
            buildChildren()
        }
        return children.toTypedArray()
    }

    private fun buildChildren() {
        val abTestCampaigns = ArrayList<Campaign>()
        val toggleCampaigns = ArrayList<Campaign>()
        val persoCampaigns = ArrayList<Campaign>()
        val deploymentCampaigns = ArrayList<Campaign>()
        val flagCampaigns = ArrayList<Campaign>()
        val customCampaigns = ArrayList<Campaign>()
        project.campaign?.map {
            when (it.type) {
                "ab" -> abTestCampaigns.add(it)
                "toggle" -> toggleCampaigns.add(it)
                "perso" -> persoCampaigns.add(it)
                "deployment" -> deploymentCampaigns.add(it)
                "flag" -> flagCampaigns.add(it)
                "custom" -> customCampaigns.add(it)
                else -> {}
            }
        }

        if (abTestCampaigns.isNotEmpty()) {
            children.add(ABTestCampaignNodeParent(abTestCampaigns))
        }
        if (toggleCampaigns.isNotEmpty()) {
            children.add(ToggleCampaignNodeParent(toggleCampaigns))
        }
        if (persoCampaigns.isNotEmpty()) {
            children.add(PersonalisationCampaignNodeParent(persoCampaigns))
        }
        if (deploymentCampaigns.isNotEmpty()) {
            children.add(DeploymentCampaignNodeParent(deploymentCampaigns))
        }
        if (flagCampaigns.isNotEmpty()) {
            children.add(FlagCampaignNodeParent(flagCampaigns))
        }
        if (customCampaigns.isNotEmpty()) {
            children.add(CustomizationCampaignNodeParent(customCampaigns))
        }

    }

    override fun update(data: PresentationData) {
        super.update(data)

        data.presentableText = viewModel.projectName
        data.setIcon(AllIcons.Nodes.Folder)
    }
}
