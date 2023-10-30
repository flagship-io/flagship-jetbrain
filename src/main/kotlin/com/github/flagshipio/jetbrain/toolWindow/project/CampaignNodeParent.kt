package com.github.flagshipio.jetbrain.toolWindow.project

import com.github.flagshipio.jetbrain.toolWindow.RootNode
import com.intellij.icons.AllIcons.Debugger
import com.intellij.ide.projectView.PresentationData
import com.intellij.ui.treeStructure.SimpleNode

class CampaignNodeParent(private var viewModel: CampaignNodeViewModel) : SimpleNode() {
    private var children: MutableList<SimpleNode> = ArrayList()
    val name_: String? get() = viewModel.campaign.name

    val campaign get() = viewModel.campaign

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
        children.add(RootNode("Id: ${viewModel.campaignId}", Debugger.Db_muted_breakpoint))
        children.add(RootNode("$NAME_PREFIX ${viewModel.campaignName}", Debugger.Db_muted_breakpoint))
        children.add(RootNode("Type: ${viewModel.campaignType}", Debugger.Db_muted_breakpoint))
        children.add(RootNode("Description: ${viewModel.campaignDescription}", Debugger.Db_muted_breakpoint))
        children.add(RootNode("Status: ${viewModel.campaignStatus}", Debugger.Db_muted_breakpoint))
        campaign.variationGroups.forEach{
            val vgViewModel = VariationGroupNodeViewModel(it)
            children.add(VariationGroupNodeParent(vgViewModel))
        }
        val schedulerViewModel = SchedulerNodeViewModel(viewModel.campaignScheduler!!)
        children.add(SchedulerNodeParent(schedulerViewModel))

    }

    override fun update(data: PresentationData) {
        super.update(data)

        data.presentableText = "Campaign: "+ viewModel.campaignName
    }
}
