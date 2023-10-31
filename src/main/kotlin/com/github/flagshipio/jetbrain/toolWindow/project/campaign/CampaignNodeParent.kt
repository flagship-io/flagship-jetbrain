package com.github.flagshipio.jetbrain.toolWindow.project.campaign

import com.github.flagshipio.jetbrain.dataClass.Campaign
import com.github.flagshipio.jetbrain.toolWindow.RootNode
import com.intellij.ide.projectView.PresentationData
import com.intellij.ui.treeStructure.SimpleNode

class CampaignNodeParent(private var campaigns: ArrayList<Campaign>?) : SimpleNode() {
    private var children: MutableList<SimpleNode> = ArrayList()

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
        if (campaigns == null || campaigns!!.size == 0) {
            children.add(RootNode("No Campaigns"))
            return
        }

        campaigns?.forEach {
            val campaignViewModel = CampaignNodeViewModel(it)
            children.add(CampaignListNodeParent(campaignViewModel))
        }
    }

    override fun update(data: PresentationData) {
        super.update(data)

        data.presentableText = "Campaign List"
    }
}
