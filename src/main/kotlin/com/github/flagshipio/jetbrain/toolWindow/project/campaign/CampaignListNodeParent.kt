package com.github.flagshipio.jetbrain.toolWindow.project.campaign

import com.github.flagshipio.jetbrain.toolWindow.project.campaign.scheduler.SchedulerNodeParent
import com.github.flagshipio.jetbrain.toolWindow.project.campaign.scheduler.SchedulerNodeViewModel
import com.github.flagshipio.jetbrain.toolWindow.project.campaign.variationGroup.VariationGroupNodeParent
import com.intellij.icons.AllIcons
import com.intellij.icons.AllIcons.Debugger
import com.intellij.ide.projectView.PresentationData
import com.intellij.ui.treeStructure.SimpleNode

class CampaignListNodeParent(private var viewModel: CampaignNodeViewModel) : SimpleNode() {
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
        children.add(VariationGroupNodeParent(campaign.variationGroups))
        val schedulerViewModel = SchedulerNodeViewModel(viewModel.campaignScheduler!!)
        children.add(SchedulerNodeParent(schedulerViewModel))

    }

    override fun update(data: PresentationData) {
        super.update(data)

        data.presentableText = viewModel.campaignName
        data.tooltip = "Description: ${viewModel.campaignDescription}"

        when (viewModel.campaignStatus){
            "active" -> data.setIcon(AllIcons.Actions.Execute)
            "paused" -> data.setIcon(Debugger.Db_no_suspend_breakpoint)
            "interrupted" -> data.setIcon(Debugger.Db_set_breakpoint)
            else -> data.setIcon(Debugger.Db_muted_breakpoint)
        }
    }
}
